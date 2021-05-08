import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceVehicleSearch extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldSerialNumber;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldBrand;
    private JTextField textFieldModel;
    private JTextField textFieldClientRFC;
    private JTextField textFieldClientName;
    private JButton buttonClientSearch;
    private JCheckBox checkBoxInMaintenance;

    public MaintenanceVehicleSearch() {
        super("Buscar Vehiculo para Mantenimiento");
        setContentPane(panelMain);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        buttonSearch.addActionListener(e -> {
            String serialNumber = textFieldSerialNumber.getText();

            String clientNameQuery = "SELECT name FROM cliente WHERE rfc = ?";
            String vehicleQuery = "SELECT * FROM coche_mantenimiento WHERE serial_number = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement statementName = conn.prepareStatement(clientNameQuery);
                 PreparedStatement searchVehicle = conn.prepareStatement(vehicleQuery)) {
                searchVehicle.setString(1, serialNumber);
                try (ResultSet vehicle = searchVehicle.executeQuery()) {
                    if (vehicle.next()) {
                        String brand = vehicle.getString("brand");
                        String model = vehicle.getString("model");
                        String clientRFC = vehicle.getString("rfc_client");
                        String inMaintenance = vehicle.getString("inMaintenance");

                        statementName.setString(1, clientRFC);
                        String clientName = "";
                        try (ResultSet client = statementName.executeQuery()) {
                            if (client.next()) {
                                clientName = client.getString("name");
                            }
                        }

                        textFieldBrand.setText(brand);
                        textFieldModel.setText(model);
                        textFieldClientRFC.setText(clientRFC);
                        textFieldClientName.setText(clientName);
                        if (inMaintenance.equals("1")) {
                            checkBoxInMaintenance.setSelected(true);
                        }

                        textFieldSerialNumber.setEditable(false);
                        textFieldBrand.setEditable(true);
                        textFieldModel.setEditable(true);
                        textFieldClientRFC.setEditable(true);
                        checkBoxInMaintenance.setEnabled(true);

                        buttonSearch.setEnabled(false);
                        buttonEdit.setEnabled(true);
                        buttonClientSearch.setEnabled(true);
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

        buttonEdit.addActionListener(e -> {
            String serialNumber = textFieldSerialNumber.getText();
            String brand = textFieldBrand.getText();
            String model = textFieldModel.getText();
            String clientRfc = textFieldClientRFC.getText();
            String clientName = textFieldClientName.getText();
            String inMaintenance;
            if (checkBoxInMaintenance.isSelected())
                inMaintenance = "1";
            else
                inMaintenance = "0";

            if (CheckUtils.validateVehicleData(this, serialNumber, brand, model, clientName)) {
                String clientQuery = "SELECT * FROM cliente WHERE rfc = ?";
                String updateQuery = "UPDATE coche_mantenimiento SET brand = ?, model = ?, rfc_client = ?, inMaintenance = ? WHERE serial_number = ?";
                try (Connection conn = Connect.getConnection();
                    PreparedStatement updateVehicle = conn.prepareStatement(updateQuery);
                    PreparedStatement searchClient = conn.prepareStatement(clientQuery)) {
                    searchClient.setString(1, clientRfc);
                    try (ResultSet client = searchClient.executeQuery()) {
                        if (client.next()) {
                            updateVehicle.setString(1, brand);
                            updateVehicle.setString(2, model);
                            updateVehicle.setString(3, clientRfc);
                            updateVehicle.setString(4, inMaintenance);
                            updateVehicle.setString(5, serialNumber);
                            updateVehicle.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Vehiculo modificado exitosamente.");
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                        }
                    }
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });

        buttonClientSearch.addActionListener(e -> {
            String clientRfc = textFieldClientRFC.getText();

            String query = "SELECT name FROM cliente WHERE rfc = ?";
            try (Connection conn = Connect.getConnection(); PreparedStatement searchClient = conn.prepareStatement(query)) {
                searchClient.setString(1, clientRfc);
                try (ResultSet client = searchClient.executeQuery()) {
                    if (client.next()) {
                        textFieldClientName.setText(client.getString("name"));
                        textFieldClientRFC.setEditable(false);
                        buttonClientSearch.setEnabled(false);
                    }
                    else {
                        textFieldClientName.setText("");
                        JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });
    }
}
