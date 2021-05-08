import javax.swing.*;
import java.sql.*;

public class ClientSearch extends JFrame {
    private JPanel panelMain;
    private JLabel labelRFC;
    private JTextField textFieldRFC;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JLabel labelName;
    private JTextField textFieldName;
    private JLabel labelPhone;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail;
    private JTextField textFieldAddress;
    private JLabel labelEmail;
    private JLabel labelAddress;

    public ClientSearch() {
        super("Buscar Cliente");
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

            String query = "SELECT * FROM cliente WHERE rfc = ?";
            try (Connection conn = Connect.getConnection(); PreparedStatement searchClient = conn.prepareStatement(query)) {
                searchClient.setString(1, rfc);
                try (ResultSet client = searchClient.executeQuery()) {
                    if (client.next()) {
                        String name = client.getString("name");
                        String phone = client.getString("phone");
                        String email = client.getString("email");
                        String address = client.getString("address");

                        textFieldName.setText(name);
                        textFieldPhone.setText(phone);
                        textFieldEmail.setText(email);
                        textFieldAddress.setText(address);

                        textFieldRFC.setEditable(false);
                        textFieldName.setEditable(true);
                        textFieldPhone.setEditable(true);
                        textFieldEmail.setEditable(true);
                        textFieldAddress.setEditable(true);

                        buttonSearch.setEnabled(false);
                        buttonEdit.setEnabled(true);
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

        buttonEdit.addActionListener(e -> {
            String rfc = textFieldRFC.getText();
            String name = textFieldName.getText();
            String phone = textFieldPhone.getText();
            String email = textFieldEmail.getText();
            String address = textFieldAddress.getText();

            if (CheckUtils.validateClientData(this, rfc, name, phone, email, address)) {
                String updateQuery = "UPDATE cliente SET name = ?, phone = ?, email = ?, address = ? WHERE rfc = ?";
                try (Connection conn = Connect.getConnection();
                    PreparedStatement updateClient = conn.prepareStatement(updateQuery)) {
                    updateClient.setString(1, name);
                    updateClient.setString(2, phone);
                    updateClient.setString(3, email);
                    updateClient.setString(4, address);
                    updateClient.setString(5, rfc);
                    updateClient.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Cliente modificado exitosamente.");
                    dispose();
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
                }
            }
        });
    }
}
