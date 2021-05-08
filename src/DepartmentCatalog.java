import javax.swing.*;

public class DepartmentCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public DepartmentCatalog() {
        super("Departamentos");
        setContentPane(panelMain);
        // pack();
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        String[] columnNames = {"ID", "Nombre", "RFC del Gerente", "Nombre del Gerente"};
        Object[][] clientData = {};

        table = new JTable(clientData, columnNames);

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
