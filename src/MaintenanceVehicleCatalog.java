import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MaintenanceVehicleCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public MaintenanceVehicleCatalog() {
        super("Vehiculos para Mantenimiento");
        setContentPane(panelMain);
        // pack();
        setSize(1100, 350);
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

        tableModel.addColumn("Numero de Serie");
        tableModel.addColumn("Marca");
        tableModel.addColumn("Modelo");
        tableModel.addColumn("RFC Cliente");
        tableModel.addColumn("Nombre del cliente");
        tableModel.addColumn("En mantenimiento");

        try (Connection conn = Connect.getConnection(); Statement statement = conn.createStatement()) {
            HashMap<String, String> clientsInfo = new HashMap<>();
            try (ResultSet clients = statement.executeQuery("SELECT rfc,name FROM cliente")) {
                while (clients.next()) {
                    clientsInfo.put(clients.getString("rfc"), clients.getString("name"));
                }
            }

            try (ResultSet vehicles = statement.executeQuery("SELECT * FROM coche_mantenimiento")) {
                while (vehicles.next()) {
                    String serialNumber = vehicles.getString("serial_number");
                    String brand = vehicles.getString("brand");
                    String model = vehicles.getString("model");
                    String rfcClient = vehicles.getString("rfc_client");
                    String inMaintenance = vehicles.getString("inMaintenance");
                    if (inMaintenance.equals("1"))
                        inMaintenance = "Si";
                    else
                        inMaintenance = "No";

                    String[] data = {serialNumber, brand, model, rfcClient, clientsInfo.get(rfcClient), inMaintenance};
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
