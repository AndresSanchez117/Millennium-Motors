import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProviderSearch extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldRFC;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JLabel labelRFC;
    private JLabel labelName;
    private JLabel labelEmail;

    public ProviderSearch() {
        super("Buscar Proveedor");
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
            String rfc = textFieldRFC.getText();

            String query = "SELECT * FROM proveedor WHERE rfc = ?";
            try (Connection conn = Connect.getConnection(); PreparedStatement searchProvider = conn.prepareStatement(query)) {
                searchProvider.setString(1, rfc);
                try (ResultSet provider = searchProvider.executeQuery()) {
                    if (provider.next()) {
                        String name = provider.getString("name");
                        String email = provider.getString("email");

                        textFieldName.setText(name);
                        textFieldEmail.setText(email);

                        textFieldRFC.setEditable(false);
                        textFieldName.setEditable(true);
                        textFieldEmail.setEditable(true);

                        buttonSearch.setEnabled(false);
                        buttonEdit.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
                    }
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
            }
        });

        buttonEdit.addActionListener(e -> {
            String rfc = textFieldRFC.getText();
            String name = textFieldName.getText();
            String email = textFieldEmail.getText();

            if (CheckUtils.validateProviderData(this, rfc, name, email)) {
                String updateQuery = "UPDATE proveedor SET name = ?, email = ? WHERE rfc = ?";
                try (Connection conn = Connect.getConnection();
                    PreparedStatement updateProvider = conn.prepareStatement(updateQuery)) {
                    updateProvider.setString(1, name);
                    updateProvider.setString(2, email);
                    updateProvider.setString(3, rfc);
                    updateProvider.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Proveedor modificado exitosamente.");
                    dispose();
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });
    }
}
