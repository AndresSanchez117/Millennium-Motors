import javax.swing.*;

public class PartsPurchaseSearch extends JFrame {
    private JPanel panelMain;
    private JPanel panelBottom;
    private JPanel panelTop;
    private JScrollPane scrollPaneTable;
    private JTextField textFieldFolio;
    private JButton buttonSearch;
    private JTextField textField1;
    private JTextField textFieldProviderRFC;
    private JTextField textFieldProviderName;
    private JTable table;

    public PartsPurchaseSearch() {
        super("Busqueda de Compra de Piezas");
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
