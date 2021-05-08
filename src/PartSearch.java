import javax.swing.*;
import java.sql.*;

public class PartSearch extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldID;
    private JTextField textFieldNumber;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldBrand;
    private JTextField textFieldModel;
    private JTextField textFieldDescription;
    private JTextField textFieldProviderRFC;
    private JTextField textFieldProviderName;
    private JTextField textFieldPrice;
    private JButton buttonProviderSearch;

    public PartSearch() {
        super("Buscar Pieza");
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
            String id = textFieldID.getText();

            String providerNameQuery = "SELECT name FROM proveedor WHERE rfc = ?";
            String partQuery = "SELECT * FROM inventario_de_piezas WHERE id = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement providerName = conn.prepareStatement(providerNameQuery);
                 PreparedStatement searchPart = conn.prepareStatement(partQuery)) {
                searchPart.setString(1, id);
                try (ResultSet part = searchPart.executeQuery()) {
                    if (part.next()) {
                        String stock = part.getString("stock");
                        String brand = part.getString("brand");
                        String model = part.getString("model");
                        String description = part.getString("description");
                        String price = part.getString("unitary_price");
                        String providerRfc = part.getString("rfc_provider");

                        providerName.setString(1, providerRfc);
                        String provName = "";
                        try (ResultSet name = providerName.executeQuery()) {
                            if (name.next()) {
                                provName = name.getString("name");
                            }
                        }

                        textFieldNumber.setText(stock);
                        textFieldBrand.setText(brand);
                        textFieldModel.setText(model);
                        textFieldDescription.setText(description);
                        textFieldProviderRFC.setText(providerRfc);
                        textFieldProviderName.setText(provName);
                        textFieldPrice.setText(price);

                        textFieldID.setEditable(false);
                        textFieldNumber.setEditable(true);
                        textFieldBrand.setEditable(true);
                        textFieldModel.setEditable(true);
                        textFieldDescription.setEditable(true);
                        textFieldProviderRFC.setEditable(true);
                        textFieldPrice.setEditable(true);

                        buttonSearch.setEnabled(false);
                        buttonEdit.setEnabled(true);
                        buttonProviderSearch.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Parte no encontrada.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });

        buttonEdit.addActionListener(e -> {
            String id = textFieldID.getText();
            String stock = textFieldNumber.getText();
            String brand = textFieldBrand.getText();
            String model = textFieldModel.getText();
            String description = textFieldDescription.getText();
            String price = textFieldPrice.getText();
            String providerRFC = textFieldProviderRFC.getText();

            if (CheckUtils.validatePartData(this, stock, brand, model, description, price)) {
                String providerQuery = "SELECT * FROM proveedor WHERE rfc = ?";
                String updateQuery = "UPDATE inventario_de_piezas SET stock = ?, brand = ?, model = ?, description = ?, unitary_price = ?, rfc_provider = ? WHERE id = ?";
                try (Connection conn = Connect.getConnection();
                    PreparedStatement updatePart = conn.prepareStatement(updateQuery);
                    PreparedStatement searchProvider = conn.prepareStatement(providerQuery)) {
                    searchProvider.setString(1, providerRFC);
                    try (ResultSet provider = searchProvider.executeQuery()) {
                        if (provider.next()) {
                            updatePart.setString(1, stock);
                            updatePart.setString(2, brand);
                            updatePart.setString(3, model);
                            updatePart.setString(4, description);
                            updatePart.setString(5, price);
                            updatePart.setString(6, providerRFC);
                            updatePart.setString(7, id);
                            updatePart.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Pieza modificada exitosamente.");
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
                        }
                    }
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });

        buttonProviderSearch.addActionListener(e -> {
            String providerRfc = textFieldProviderRFC.getText();

            String query = "SELECT name FROM proveedor WHERE rfc = ?";
            try (Connection conn = Connect.getConnection(); PreparedStatement searchProvider = conn.prepareStatement(query)) {
                searchProvider.setString(1, providerRfc);
                try (ResultSet provider = searchProvider.executeQuery()) {
                    if (provider.next()) {
                        textFieldProviderName.setText(provider.getString("name"));
                        textFieldProviderRFC.setEditable(false);
                        buttonProviderSearch.setEnabled(false);
                    }
                    else {
                        textFieldProviderName.setText("");
                        JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });
    }
}
