import javax.swing.*;

public class DepartmentSearch extends JFrame {

    private JPanel panelMain;
    private JLabel labelID;
    private JTextField textFieldID;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldManagerRFC;
    private JTextField textFieldManagerName;
    private JTextField textFieldName;
    private JLabel labelManagerRFC;
    private JLabel labelManagerName;
    private JLabel labelName;

    public DepartmentSearch() {
        super("Buscar Departamento");
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
