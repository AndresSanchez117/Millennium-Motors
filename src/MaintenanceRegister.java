import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MaintenanceRegister extends JFrame {
    private JPanel panelMain;
    private JPanel panelBottom;
    private JPanel panelTop;
    private JScrollPane scrollPaneTable;
    private JLabel labelFolioNumber;
    private JTextField textFieldDate;
    private JTextField textFieldMotive;
    private JTextField textFieldWorkForceCost;
    private JTextField textFieldPartID;
    private JTextField textFieldPartModel;
    private JTextField textFieldPartCost;
    private JTextField textFieldNumber;
    private JButton buttonAddPart;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JTextField textFieldClientName;
    private JTextField textFieldVehicleNumber;
    private JButton buttonSearchVehicle;
    private JTextField textFieldVehicleModel;
    private JButton buttonPartSearch;
    private JLabel labelPartsSubTotalValue;
    private JLabel labelTotalValue;
    private JButton buttonUpdateWorkforceCost;
    private JTable table;
    private ArrayList<PartDetail> usedParts;

    public MaintenanceRegister() {
        super("Registro de Servicio de Mantenimiento");
        setContentPane(panelMain);
        pack();
        //setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        usedParts = new ArrayList<>();
        labelPartsSubTotalValue.setText("0");
        labelTotalValue.setText("0");

        // Initialize Folio
        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            try (ResultSet maxFolio = statement.executeQuery("SELECT MAX(folio) AS maxFolio FROM servicio_de_mantenimiento")) {
                String folio = null;
                if (maxFolio.next()) {
                    try {
                        folio = maxFolio.getString("maxFolio");
                        int newFolio = Integer.parseInt(folio) + 1;
                        folio = Integer.toString(newFolio);
                    } catch (NumberFormatException exception) {
                        folio = null;
                    }
                }

                if (folio == null) {
                    folio = "1";
                }
                labelFolioNumber.setText(folio);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
        }

        // Initialize date
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        textFieldDate.setText(formattedDate);

        // Initialize parts table
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("ID Pieza");
        tableModel.addColumn("Modelo de Pieza");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Precio Unitario");

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);

        // Button behavior
        buttonCancel.addActionListener(e -> {
            dispose();
        });

        buttonRegister.addActionListener(e -> {
            String folio = labelFolioNumber.getText();
            long epochDate = LocalDate.now().toEpochDay();
            String epochDateString = Long.toString(epochDate);
            String serialNumber = textFieldVehicleNumber.getText();
            String clientName = textFieldClientName.getText();
            String motive = textFieldMotive.getText();
            String workforceCost = textFieldWorkForceCost.getText();

            if (CheckUtils.validateServiceData(this, clientName, motive, workforceCost)) {
                String query = "INSERT INTO servicio_de_mantenimiento VALUES(?,?,?,?,?)";
                String detailQuery = "INSERT INTO detalle_servicio_de_mantenimiento VALUES(?,?,?)";
                String updateInventoryQuery = "UPDATE inventario_de_piezas SET stock = (stock - ?) WHERE id = ?";
                String updateVehicleQuery = "UPDATE coche_mantenimiento SET inMaintenance = 0 WHERE serial_number = ?";
                try (Connection conn = Connect.getConnection();
                    PreparedStatement insertService = conn.prepareStatement(query);
                    PreparedStatement insertDetail = conn.prepareStatement(detailQuery);
                    PreparedStatement updateInventory = conn.prepareStatement(updateInventoryQuery);
                    PreparedStatement updateVehicle = conn.prepareStatement(updateVehicleQuery)) {

                    // Insert service
                    insertService.setString(1, folio);
                    insertService.setString(2, epochDateString);
                    insertService.setString(3, serialNumber);
                    insertService.setString(4, motive);
                    insertService.setString(5, workforceCost);
                    insertService.executeUpdate();

                    // Insert details and update inventory
                    for (int i = 0; i < usedParts.size(); i++) {
                        PartDetail partDetail = usedParts.get(i);
                        String partID = partDetail.partID;
                        String amountUsed = partDetail.amountUsed;
                        int iAmountUsed = Integer.parseInt(amountUsed);

                        // Insert detail
                        insertDetail.setString(1, folio);
                        insertDetail.setString(2, partID);
                        insertDetail.setString(3, amountUsed);
                        insertDetail.executeUpdate();

                        // Update inventory
                        updateInventory.setInt(1, iAmountUsed);
                        updateInventory.setString(2, partID);
                        updateInventory.executeUpdate();
                    }

                    // Update vehicle status
                    updateVehicle.setString(1, serialNumber);
                    updateVehicle.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Servicio de matenimiento registrado exitosamente.");
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });

        buttonSearchVehicle.addActionListener(e -> {
            String serialNumber = textFieldVehicleNumber.getText();

            String query = "SELECT model,rfc_client,inMaintenance FROM coche_mantenimiento WHERE serial_number = ?";
            String nameQuery = "SELECT name FROM cliente WHERE rfc = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement searchVehicle = conn.prepareStatement(query);
                 PreparedStatement searchClientName = conn.prepareStatement(nameQuery)) {
                searchVehicle.setString(1, serialNumber);
                try (ResultSet vehicle = searchVehicle.executeQuery()) {
                    if (vehicle.next()) {
                        String inMaintenance = vehicle.getString("inMaintenance");
                        if (inMaintenance.equals("1")) {
                            String clientRfc = vehicle.getString("rfc_client");
                            searchClientName.setString(1, clientRfc);
                            String clientName = "";
                            try (ResultSet client = searchClientName.executeQuery()) {
                                if (client.next()) {
                                    clientName = client.getString("name");
                                }
                            }

                            textFieldVehicleModel.setText(vehicle.getString("model"));
                            textFieldClientName.setText(clientName);

                            textFieldVehicleNumber.setEditable(false);
                            buttonSearchVehicle.setEnabled(false);
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Este vehiculo no está en mantenimiento.");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Vehiculo no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });

        buttonPartSearch.addActionListener(e -> {
            String partId = textFieldPartID.getText();

            String query = "SELECT model,unitary_price FROM inventario_de_piezas WHERE id = ?";
            try (Connection conn = Connect.getConnection(); PreparedStatement searchPart = conn.prepareStatement(query)) {
                searchPart.setString(1, partId);
                try (ResultSet part = searchPart.executeQuery()) {
                    if (part.next()) {
                        textFieldPartModel.setText(part.getString("model"));
                        textFieldPartCost.setText(part.getString("unitary_price"));

                        textFieldPartID.setEditable(false);
                        buttonPartSearch.setEnabled(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });

        buttonAddPart.addActionListener(e -> {
            String partID = textFieldPartID.getText();
            String partModel = textFieldPartModel.getText();
            String partCost = textFieldPartCost.getText();
            String amount = textFieldNumber.getText();

            int iAmount = 0;
            int stock = 0;
            try {
                iAmount = Integer.parseInt(amount);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Cantidad Invalida.");
            }

            if (!buttonPartSearch.isEnabled()) {
                String query = "SELECT stock FROM inventario_de_piezas WHERE id = ?";
                try (Connection conn = Connect.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
                    statement.setString(1, partID);
                    try (ResultSet existence = statement.executeQuery()) {
                        if (existence.next()) {
                            stock = existence.getInt("stock");
                        }
                    }
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
                if (iAmount > 0 && iAmount <= stock) {
                    // Show part data in table
                    String[] data = {partID, partModel, amount, partCost};
                    tableModel.addRow(data);
                    usedParts.add(new PartDetail(partID, amount));

                    // Update part subtotal and total
                    double dPartCost = Double.parseDouble(partCost);
                    double thisPartSubtotal = dPartCost * iAmount;

                    double newSubtotal = Double.parseDouble(labelPartsSubTotalValue.getText()) + thisPartSubtotal;
                    double newTotal = Double.parseDouble(labelTotalValue.getText()) + thisPartSubtotal;

                    labelPartsSubTotalValue.setText(Double.toString(newSubtotal));
                    labelTotalValue.setText(Double.toString(newTotal));

                    // Reset part fields
                    textFieldPartID.setText("");
                    textFieldPartModel.setText("");
                    textFieldPartCost.setText("");
                    textFieldNumber.setText("");

                    textFieldPartID.setEditable(true);
                    buttonPartSearch.setEnabled(true);
                }
                else {
                    String message = "Cantidad invalida.";
                    if (iAmount > stock)
                        message = "Cantidad excede existencia.";

                    JOptionPane.showMessageDialog(this, message);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Pieza Invalida.");
            }
        });

        buttonUpdateWorkforceCost.addActionListener(e -> {
            String workforceCost = textFieldWorkForceCost.getText();
            double dWorkforceCost = 0;
            try {
                dWorkforceCost = Double.parseDouble(workforceCost);
                if (dWorkforceCost > 0) {
                    double newTotal = Double.parseDouble(labelTotalValue.getText()) + dWorkforceCost;
                    labelTotalValue.setText(Double.toString(newTotal));

                    textFieldWorkForceCost.setEditable(false);
                    buttonUpdateWorkforceCost.setEnabled(false);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Costo invalido.");
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Costo invalido.");
            }
        });
    }

    class PartDetail {
        String partID;
        String amountUsed;

        public PartDetail(String partID, String amountUsed) {
            this.partID = partID;
            this.amountUsed = amountUsed;
        }
    }
}
