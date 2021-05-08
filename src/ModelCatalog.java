import javax.swing.*;

public class ModelCatalog extends JFrame {
    private JPanel panelMain;
    private JScrollPane scrollPaneTable;
    private JTable table;

    public ModelCatalog() {
        super("Modelos");
        setContentPane(panelMain);
        // pack();
        setSize(1300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        String[] columnNames = {"Modelo", "Marca", "Tipo de Tracción", "Tipo de Motor", "Numero de cilindros", "Numero de velocidades",
                "Potencia máxima", "Torque máximo", "Aceleración de 0 a 100 (s)", "Tipo de transmisión", "Sistemas de asistencia",
                "Sistemas de seguridad", "Precio"};
        Object[][] clientData = {};

        table = new JTable(clientData, columnNames);

        scrollPaneTable.setViewportView(table);
        table.setFillsViewportHeight(true);
    }
}
