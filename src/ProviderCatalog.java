import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProviderCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public ProviderCatalog() {
        super("Proveedores");
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
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("RFC");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Email");

        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            try (ResultSet providers = statement.executeQuery("SELECT * FROM proveedor")) {
                while (providers.next()) {
                    String rfc = providers.getString("rfc");
                    String name = providers.getString("name");
                    String email = providers.getString("email");

                    String[] data = {rfc, name, email};
                    tableModel.addRow(data);
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.");
        }

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
