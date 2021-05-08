import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PartRegister extends JFrame {
    private JPanel panelMain;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JLabel labelID;
    private JTextField textFieldNumber;
    private JTextField textFieldBrand;
    private JTextField textFieldModel;
    private JTextField textFieldDescription;
    private JTextField textFieldPrice;
    private JComboBox comboBoxProvider;

    public PartRegister() {
        super("Registro de Pieza en Inventario");
        setContentPane(panelMain);
        // pack();
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            try (ResultSet providers = statement.executeQuery("SELECT * FROM proveedor")) {
                while (providers.next()) {
                    String rfc = providers.getString("rfc");
                    String name = providers.getString("name");

                    comboBoxProvider.addItem(rfc + "-" + name);
                }
            }

            try (ResultSet maxId = statement.executeQuery("SELECT MAX(id) AS maxID FROM inventario_de_piezas")) {
                String id = null;
                if (maxId.next()) {
                    try {
                        id = maxId.getString("maxID");
                        int newID = Integer.parseInt(id) + 1;
                        id = Integer.toString(newID);
                    } catch (NumberFormatException exception) {
                        id = null;
                    }
                }

                if (id == null) {
                    id = "1";
                }
                labelID.setText(id);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
        }

        buttonCancel.addActionListener(e -> {
            dispose();
        });

        buttonRegister.addActionListener(e -> {
            String id = labelID.getText();
            String stock = textFieldNumber.getText();
            String brand = textFieldBrand.getText();
            String model = textFieldModel.getText();
            String description = textFieldDescription.getText();
            String price = textFieldPrice.getText();

            String selectedProvider = (String) comboBoxProvider.getSelectedItem();
            String provider = selectedProvider.split("-")[0];

            if (CheckUtils.validatePartData(this, stock, brand, model, description, price)) {
                String query = "INSERT INTO inventario_de_piezas VALUES(?,?,?,?,?,?,?)";
                try (Connection conn = Connect.getConnection(); PreparedStatement insertPart = conn.prepareStatement(query)) {
                    insertPart.setString(1, id);
                    insertPart.setString(2, stock);
                    insertPart.setString(3, brand);
                    insertPart.setString(4, model);
                    insertPart.setString(5, description);
                    insertPart.setString(6, price);
                    insertPart.setString(7, provider);
                    insertPart.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Parte registrada exitosamente.");
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });
    }
}
