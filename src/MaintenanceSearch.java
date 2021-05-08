import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MaintenanceSearch extends JFrame {
    private JPanel panelMain;
    private JPanel panelTop;
    private JPanel panelBottom;
    private JScrollPane scrollPaneTable;
    private JTextField textFieldFolio;
    private JButton buttonSearch;
    private JTextField textFieldDate;
    private JTextField textFieldVehicleNumber;
    private JTextField textFieldVehicleModel;
    private JTextField textFieldClientName;
    private JTextField textFieldMotive;
    private JTextField textFieldWorkforceCost;
    private JLabel labelPartsSutotal;
    private JLabel labelTotal;
    private JTable table;

    public MaintenanceSearch() {
        super("Busqueda de Servicios de Mantenimiento");
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
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("ID Pieza");
        tableModel.addColumn("Modelo de Pieza");
        tableModel.addColumn("Cantidad Usada");
        tableModel.addColumn("Precio Unitario");

        buttonSearch.addActionListener(e -> {
            String folio = textFieldFolio.getText();

            String searchServiceQuery = "SELECT * FROM servicio_de_mantenimiento WHERE folio = ?";
            String searchVehicleQuery = "SELECT model,rfc_client FROM coche_mantenimiento WHERE serial_number = ?";
            String searchClientQuery = "SELECT name FROM cliente WHERE rfc = ?";
            String detailQuery = "SELECT * FROM detalle_servicio_de_mantenimiento WHERE service_folio = ?";
            String inventoryQuery = "SELECT model,unitary_price FROM inventario_de_piezas WHERE id = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement searchService = conn.prepareStatement(searchServiceQuery);
                 PreparedStatement searchVehicle = conn.prepareStatement(searchVehicleQuery);
                 PreparedStatement searchClient = conn.prepareStatement(searchClientQuery);
                 PreparedStatement detail = conn.prepareStatement(detailQuery);
                 PreparedStatement inventory = conn.prepareStatement(inventoryQuery)) {

                searchService.setString(1, folio);
                try (ResultSet service = searchService.executeQuery()) {
                    if (service.next()) {
                        // TODO: Format date
                        //String date = service.getString("date");
                        long lDate = service.getLong("date");
                        LocalDate date = LocalDate.ofEpochDay(lDate);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = date.format(formatter);
                        String serialNumber = service.getString("vehicle_number");
                        String motive = service.getString("type");
                        String workforceCost = service.getString("workforce_cost");
                        double dWorkforceCost = Double.parseDouble(workforceCost);

                        String vehicleModel = "";
                        String clientRfc = "";
                        String clientName = "";

                        searchVehicle.setString(1, serialNumber);
                        try (ResultSet vehicle = searchVehicle.executeQuery()) {
                            if (vehicle.next()) {
                                vehicleModel = vehicle.getString("model");
                                clientRfc = vehicle.getString("rfc_client");
                            }
                        }

                        searchClient.setString(1, clientRfc);
                        try (ResultSet client = searchClient.executeQuery()) {
                            if (client.next()) {
                                clientName = client.getString("name");
                            }
                        }

                        textFieldDate.setText(formattedDate);
                        textFieldVehicleNumber.setText(serialNumber);
                        textFieldVehicleModel.setText(vehicleModel);
                        textFieldClientName.setText(clientName);
                        textFieldMotive.setText(motive);
                        textFieldWorkforceCost.setText(workforceCost);

                        textFieldFolio.setEditable(false);
                        buttonSearch.setEnabled(false);

                        double dPartsSubtotal = 0;
                        detail.setString(1, folio);
                        try (ResultSet details = detail.executeQuery()) {
                            while (details.next()) {
                                String partID = details.getString("part_id");
                                String amount = details.getString("ammount");
                                int iAmount = Integer.parseInt(amount);

                                String partModel = "";
                                String unitaryPrice = "";
                                double dUnitaryPrice = 0;
                                inventory.setString(1, partID);
                                try (ResultSet part = inventory.executeQuery()) {
                                    if (part.next()) {
                                        partModel = part.getString("model");
                                        unitaryPrice = part.getString("unitary_price");
                                        dUnitaryPrice = Double.parseDouble(unitaryPrice);
                                    }
                                }

                                dPartsSubtotal += dUnitaryPrice * iAmount;

                                String[] data = {partID, partModel, amount, unitaryPrice};
                                tableModel.addRow(data);
                            }
                        }

                        double dTotal = dPartsSubtotal + dWorkforceCost;

                        labelPartsSutotal.setText(Double.toString(dPartsSubtotal));
                        labelTotal.setText(Double.toString(dTotal));
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Servicio de mantenimiento no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
