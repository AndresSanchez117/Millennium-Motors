import javax.swing.*;

public class PartsPurchaseRegister extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JPanel panelTop;
    private JPanel panelBottom;
    private JLabel labelFolioNumber;
    private JTextField textFieldDate;
    private JComboBox comboBoxProvider;
    private JButton buttonCancel;
    private JButton buttonRegister;
    private JTextField textFieldPartID;
    private JTextField textFieldPartModel;
    private JTextField textFieldNumber;
    private JTextField textFieldPrice;
    private JButton buttonAddPart;
    private JLabel labelSpace;
    private JTable table;

    public PartsPurchaseRegister() {
        super("Registro de Compra de Piezas");
        setContentPane(panelMain);
        pack();
        //setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        String[] columnNames = {"ID Pieza", "Modelo de Pieza", "Cantidad", "Precio"};
        Object[][] data = {};

        table = new JTable(data, columnNames);

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
