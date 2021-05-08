import javax.swing.*;

public class EmployeeSearch extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldRFC;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldName;
    private JTextField textFieldAddress;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldDepartmentID;
    private JTextField textFieldDepartmentName;
    private JCheckBox checkBoxActive;

    public EmployeeSearch() {
        super("Buscar Empleado");
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

    }
}
