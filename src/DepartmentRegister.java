import javax.swing.*;
import java.awt.*;

public class DepartmentRegister extends JFrame {
    private JPanel panelMain;
    private JLabel labelDepartment;
    private JLabel labelName;
    private JLabel labelDepartmentNumber;
    private JLabel labelManager;
    private JTextField textFieldName;
    private JComboBox comboBoxManager;
    private JButton buttonCancel;
    private JButton buttonRegister;

    public DepartmentRegister() {
        super("Registro de Departamento");
        setContentPane(panelMain);
        //pack();
        setSize(500, 180);
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
