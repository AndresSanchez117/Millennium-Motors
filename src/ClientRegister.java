import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientRegister extends JFrame {
    private JPanel panelMain;
    private JLabel labelRFC;
    private JLabel labelName;
    private JLabel labelPhone;
    private JLabel labelEmail;
    private JLabel labelAddress;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JTextField textFieldRFC;
    private JTextField textFieldName;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail;
    private JTextField textFieldAddress;

    public ClientRegister() {
        super("Registro de Cliente");
        setContentPane(panelMain);
        //pack();
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
//        buttonCancel.setBackground(new Color(198, 44, 57));
//        buttonCancel.setForeground(new Color(212, 215, 213));
//
//        buttonRegister.setBackground(new Color(52, 48, 153));
//        buttonRegister.setForeground(new Color(212, 215, 213));
        buttonCancel.addActionListener(e -> {
            dispose();
        });

        buttonRegister.addActionListener(e -> {
            String rfc = textFieldRFC.getText();
            String name = textFieldName.getText();
            String phone = textFieldPhone.getText();
            String email = textFieldEmail.getText();
            String address = textFieldAddress.getText();

            if (CheckUtils.validateClientData(this, rfc, name, phone, email, address)) {
                String query = "INSERT INTO cliente VALUES(?,?,?,?,?)";
                try (Connection conn = Connect.getConnection(); PreparedStatement insertClient = conn.prepareStatement(query)) {
                    insertClient.setString(1, rfc);
                    insertClient.setString(2, name);
                    insertClient.setString(3, phone);
                    insertClient.setString(4, email);
                    insertClient.setString(5, address);
                    insertClient.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");
                    dispose();
                } catch (SQLException exception) {
                    String exceptionMessage = exception.getMessage();
                    System.out.println(exceptionMessage);
                    String message;
                    if (exceptionMessage.contains("UNIQUE constraint failed: cliente.rfc")) {
                        message = "ERROR: Cliente ya registrado.";
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
