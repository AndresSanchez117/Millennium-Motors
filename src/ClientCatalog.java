import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public ClientCatalog() {
        super("Clientes");
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
        tableModel.addColumn("Telefono");
        tableModel.addColumn("Email");
        tableModel.addColumn("Domicilio");

        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            try (ResultSet clients = statement.executeQuery("SELECT * FROM cliente")) {
                while (clients.next()) {
                    String rfc = clients.getString("rfc");
                    String name = clients.getString("name");
                    String phone = clients.getString("phone");
                    String email = clients.getString("email");
                    String address = clients.getString("address");

                    String[] data = {rfc, name, phone, email, address};
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
