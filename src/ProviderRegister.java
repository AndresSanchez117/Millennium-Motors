import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProviderRegister extends JFrame {
    private JPanel panelMain;
    private JLabel labelRFC;
    private JLabel labelName;
    private JLabel labelEmail;
    private JTextField textFieldRFC;
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JButton buttonCancel;
    private JButton buttonRegister;

    public ProviderRegister() {
        super("Registro de Proveedor");
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
        buttonCancel.addActionListener(e -> {
            dispose();
        });

        buttonRegister.addActionListener(e -> {
            String rfc = textFieldRFC.getText();
            String name = textFieldName.getText();
            String email = textFieldEmail.getText();

            if (CheckUtils.validateProviderData(this, rfc, name, email)) {
                String query = "INSERT INTO proveedor VALUES(?,?,?)";
                try (Connection conn = Connect.getConnection(); PreparedStatement insertProvider = conn.prepareStatement(query)) {
                    insertProvider.setString(1, rfc);
                    insertProvider.setString(2, name);
                    insertProvider.setString(3, email);
                    insertProvider.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Proveedor registrado exitosamente.");
                    dispose();
                } catch (SQLException exception) {
                    String exceptionMessage = exception.getMessage();
                    System.out.println(exceptionMessage);
                    String message;
                    if (exceptionMessage.contains("UNIQUE constraint failed: proveedor.rfc")) {
                        message = "ERROR: Proveedor ya registrado";
                    }
                    else {
                        message = "Error en la conexión a la base de datos.";
                    }
                    JOptionPane.showMessageDialog(this, message);
                }
            }
        });
    }
}
