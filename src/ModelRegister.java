import javax.swing.*;
import java.awt.*;

public class ModelRegister extends JFrame {
    private JPanel panelMain;
    private JTextField textFieldModel;
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
    private JTextField textFieldSafety;
    private JTextField textFieldPrice;
    private JButton buttonCancel;
    private JButton buttonRegister;

    public ModelRegister() {
        super("Registro de Modelo");
        setContentPane(panelMain);
        pack();
        //setSize(600, 450);
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
    }
}
