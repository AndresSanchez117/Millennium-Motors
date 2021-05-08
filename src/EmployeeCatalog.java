import javax.swing.*;

public class EmployeeCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public EmployeeCatalog() {
        super("Empleados");
        setContentPane(panelMain);
        // pack();
        setSize(1200, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        String[] columnNames = {"RFC", "Nombre", "Domicilio", "Nombre de Usuario", "Contraseña", "ID Departamento", "Nombre Departamento", "Activo"};
        Object[][] clientData = {};

        table = new JTable(clientData, columnNames);

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
