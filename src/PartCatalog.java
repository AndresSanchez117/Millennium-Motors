import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class PartCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public PartCatalog() {
        super("Inventario de Partes");
        setContentPane(panelMain);
        // pack();
        setSize(1000, 350);
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

        tableModel.addColumn("ID");
        tableModel.addColumn("Cantidad");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Modelo");
        tableModel.addColumn("Descripción");
        tableModel.addColumn("Precio unitario");
        tableModel.addColumn("RFC Proveedor");
        tableModel.addColumn("Nombre Proveedor");


        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            HashMap<String, String> providersInfo = new HashMap<>();
            try (ResultSet providers = statement.executeQuery("SELECT rfc,name FROM proveedor")) {
                while (providers.next()) {
                    providersInfo.put(providers.getString("rfc"), providers.getString("name"));
                }
            }

            try (ResultSet parts = statement.executeQuery("SELECT * FROM inventario_de_piezas")) {
                while (parts.next()) {
                    String id = parts.getString("id");
                    String stock = parts.getString("stock");
                    String brand = parts.getString("brand");
                    String model = parts.getString("model");
                    String description = parts.getString("description");
                    String price = parts.getString("unitary_price");
                    String provider = parts.getString("rfc_provider");

                    String[] data = {id, stock, brand, model, description, price, provider, providersInfo.get(provider)};
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
