import javax.swing.*;

public class EmployeeRegister extends JFrame {
    private JPanel panelMain;
    private JLabel labelRFC;
    private JLabel labelName;
    private JLabel labelAddress;
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JLabel labelDepartment;
    private JLabel labelStatus;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JTextField textFieldRFC;
    private JPasswordField passwordField;
    private JTextField textFieldName;
    private JTextField textFieldAddress;
    private JTextField textFieldUserName;
    private JComboBox comboBoxDepartment;
    private JCheckBox checkBoxStatus;

    public EmployeeRegister() {
        super("Registro de Empleado");
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
        comboBoxDepartment.addItem("1-Ventas");;
    }
}
