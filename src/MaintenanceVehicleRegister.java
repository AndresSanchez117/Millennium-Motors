import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintenanceVehicleRegister extends JFrame {
    private JPanel panelMain;
    private JLabel labelSerialNumber;
    private JLabel labelBrand;
    private JLabel labelModel;
    private JLabel labelClientRFC;
    private JTextField textFieldSerialNumber;
    private JTextField textFieldBrand;
    private JTextField textFieldModel;
//    private JButton buttonWarrantySelect;
//    private JTextField textFieldWarranty;
//    private JTextField textFieldInsurance;
//    private JButton buttonInsuranceSelect;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JPanel panelClient;
    private JTextField textFieldClientRFC;
    private JButton buttonClientSearch;
    private JLabel labelClientName;
    private JTextField textFieldClientName;
    private JCheckBox checkBoxInMaintenance;

    public MaintenanceVehicleRegister() {
        super("Registro de Vehiculo para Mantenimiento");
        setContentPane(panelMain);
        //pack();
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        buttonCancel.addActionListener(e -> {
            dispose();
        });

        buttonRegister.addActionListener(e -> {
            String serialNumber = textFieldSerialNumber.getText();
            String brand = textFieldBrand.getText();
            String model = textFieldModel.getText();
            String clientRFC = textFieldClientRFC.getText();
            String clientName = textFieldClientName.getText();

            if (CheckUtils.validateVehicleData(this, serialNumber, brand, model, clientName)) {
                String query = "INSERT INTO coche_mantenimiento(serial_number,brand,model,rfc_client) VALUES(?,?,?,?)";
                try (Connection conn = Connect.getConnection(); PreparedStatement insertVehicle = conn.prepareStatement(query)) {
                    insertVehicle.setString(1, serialNumber);
                    insertVehicle.setString(2, brand);
                    insertVehicle.setString(3, model);
                    insertVehicle.setString(4, clientRFC);
                    insertVehicle.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Vehiculo registrado exitosamente.");
                    dispose();
                } catch (SQLException exception) {
                    String exceptionMessage = exception.getMessage();
                    System.out.println(exceptionMessage);
                    String message;
                    if (exceptionMessage.contains("UNIQUE constraint failed: coche_mantenimiento.serial_number")) {
                        message = "ERROR: Vehiculo ya registrado.";
                    }
                    else {
                        message = "Error en la conexión a la base de datos.";
                    }
                    JOptionPane.showMessageDialog(this, message);
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
                        JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });
//        // Button actions
//        buttonWarrantySelect.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int returnValue = fileChooser.showOpenDialog(null);
//            if (returnValue == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//                textFieldWarranty.setText(selectedFile.getName());
//            }
//        });
//
//        buttonInsuranceSelect.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int returnValue = fileChooser.showOpenDialog(null);
//            if (returnValue == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//                textFieldInsurance.setText(selectedFile.getName());
//            }
//        });
    }
}
