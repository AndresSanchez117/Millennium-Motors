import javax.swing.*;

public class ModelSearch extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldModel;
    private JButton buttonSearch;
    private JButton buttonEdit;
    private JTextField textFieldBrand;
    private JTextField textFieldTraction;
    private JTextField textFieldMotor;
    private JTextField textFieldCilinders;
    private JTextField textFieldSpeeds;
    private JTextField textFieldMaxPower;
    private JTextField textFieldMaxTorque;
    private JTextField textFieldAcceleration;
    private JTextField textFieldTransmission;
    private JTextField textFieldAssistence;
    private JTextField textField1;
    private JTextField textFieldPrice;
    private JLabel labelSecurity;
    private JLabel labelPrice;

    public ModelSearch() {
        super("Buscar Modelo");
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
