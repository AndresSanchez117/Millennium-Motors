import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame {
    private JPanel panelMain;
    private JPanel panelCopyright;
    private JLabel labelCopyright;
    private JPanel panelLogo;

    public MainForm() {
        // Set up the window
        super("Sistema de Mantenimiento de Milenium Motors");
        setContentPane(panelMain);
        //pack();
        setSize(750, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Make components visible
        setVisible(true);
    }

    private void initializeComponents() {
        initializeMenuBar();

        // Panel with Milenium logo
        try {
            BufferedImage mileniumLogo = ImageIO.read(new File("/home/andres/Ing/5-IngenieriaDeSoftware/MileniumProject/resources/mileniumLogo.png"));
            JLabel labelLogo = new JLabel(new ImageIcon(mileniumLogo));
            panelLogo.add(labelLogo, BorderLayout.CENTER);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Maintenance service menu
        JMenu menuMaintenance = new JMenu("Servicio de Mantenimiento");
        menuMaintenance.setMnemonic(KeyEvent.VK_S);
        // Maintenance service menu items
        JMenuItem menuMaintenanceRegister = new JMenuItem("Registrar");
        JMenuItem menuMaintenanceSearch = new JMenuItem("Buscar");
        // Add menu items to maintenance menu
        menuMaintenance.add(menuMaintenanceRegister);
        menuMaintenance.add(menuMaintenanceSearch);

//        // Parts purchase menu
//        JMenu menuPartsPurchase = new JMenu("Compra de Piezas");
//        menuPartsPurchase.setMnemonic(KeyEvent.VK_C);
//        // Parts purchase menu items
//        JMenuItem menuPartsPurchaseRegister = new JMenuItem("Registrar");
//        JMenuItem menuPartsPurchaseSearch = new JMenuItem("Buscar");
//        // Add menu items to parts purchase menu
//        menuPartsPurchase.add(menuPartsPurchaseRegister);
//        menuPartsPurchase.add(menuPartsPurchaseSearch);

        // Maintenance vehicle menu
        JMenu menuMaintenanceVehicle = new JMenu("Vehiculos para Mantenimiento");
        menuMaintenanceVehicle.setMnemonic(KeyEvent.VK_V);
        // Maintenance vehicle menu items
        JMenuItem menuMaintenanceVehicleRegister = new JMenuItem("Registrar");
        JMenuItem menuMaintenanceVehicleCatalog = new JMenuItem("Ver Todo");
        JMenuItem menuMaintenanceVehicleSearch = new JMenuItem("Buscar/Editar");
        // Add menu items to menu
        menuMaintenanceVehicle.add(menuMaintenanceVehicleRegister);
        menuMaintenanceVehicle.add(menuMaintenanceVehicleCatalog);
        menuMaintenanceVehicle.add(menuMaintenanceVehicleSearch);

        // Parts menu
        JMenu menuParts = new JMenu("Inventario de Piezas");
        menuParts.setMnemonic(KeyEvent.VK_I);
        // Parts menu items
        JMenuItem menuPartsRegister= new JMenuItem("Registrar");
        JMenuItem menuPartsCatalog= new JMenuItem("Ver Todo");
        JMenuItem menuPartsSearch = new JMenuItem("Buscar/Editar");
        // Add parts menu items to menu
        menuParts.add(menuPartsRegister);
        menuParts.add(menuPartsCatalog);
        menuParts.add(menuPartsSearch);

//        // Employee menu
//        JMenu menuEmployee = new JMenu("Empleados");
//        menuEmployee.setMnemonic(KeyEvent.VK_E);
//        // Employee menu items
//        JMenuItem menuEmployeeRegister = new JMenuItem("Registrar");
//        JMenuItem menuEmployeeCatalog = new JMenuItem("Ver Todo");
//        JMenuItem menuEmployeeSearch = new JMenuItem("Buscar/Editar");
//        // Add menu items to employees menu
//        menuEmployee.add(menuEmployeeRegister);
//        menuEmployee.add(menuEmployeeCatalog);
//        menuEmployee.add(menuEmployeeSearch);
//
//        // Department menu
//        JMenu menuDepartment = new JMenu("Departamentos");
//        menuDepartment.setMnemonic(KeyEvent.VK_D);
//        // Department menu items
//        JMenuItem menuDepartmentRegister = new JMenuItem("Registrar");
//        JMenuItem menuDepartmentCatalog = new JMenuItem("Ver Todo");
//        JMenuItem menuDepartmentSearch = new JMenuItem("Buscar/Editar");
//        // Add menu items to department menu
//        menuDepartment.add(menuDepartmentRegister);
//        menuDepartment.add(menuDepartmentCatalog);
//        menuDepartment.add(menuDepartmentSearch);

        // Client menu
        JMenu menuClient = new JMenu("Clientes");
        menuClient.setMnemonic(KeyEvent.VK_C);
        // Client menu items
        JMenuItem menuClientRegister = new JMenuItem("Registrar");
        JMenuItem menuClientCatalog = new JMenuItem("Ver Todo");
        JMenuItem menuClientSearch = new JMenuItem("Buscar/Editar");
        // Add menu items to client menu
        menuClient.add(menuClientRegister);
        menuClient.add(menuClientCatalog);
        menuClient.add(menuClientSearch);

        // Provider menu
        JMenu menuProvider = new JMenu("Proveedores");
        menuProvider.setMnemonic(KeyEvent.VK_P);
        // Provider menu items
        JMenuItem menuProviderRegister = new JMenuItem("Registrar");
        JMenuItem menuProviderCatalog = new JMenuItem("Ver Todo");
        JMenuItem menuProviderSearch = new JMenuItem("Buscar/Editar");
        // Add menu items to provider menu
        menuProvider.add(menuProviderRegister);
        menuProvider.add(menuProviderCatalog);
        menuProvider.add(menuProviderSearch);

        // Add menus to the menu bar
        menuBar.add(menuMaintenance);
        // menuBar.add(menuPartsPurchase);
        menuBar.add(menuMaintenanceVehicle);
        menuBar.add(menuParts);
        // menuBar.add(menuEmployee);
        // menuBar.add(menuDepartment);
        menuBar.add(menuClient);
        menuBar.add(menuProvider);

        // Menu bar actions
        // Maintenance menu
        menuMaintenanceRegister.addActionListener(e -> new MaintenanceRegister());
        menuMaintenanceSearch.addActionListener(e -> new MaintenanceSearch());
        // Parts purchase menu
//        menuPartsPurchaseRegister.addActionListener(e -> new PartsPurchaseRegister());
//        menuPartsPurchaseSearch.addActionListener(e -> new PartsPurchaseSearch());
        // Maintenance vehicle menu
        menuMaintenanceVehicleRegister.addActionListener(e -> new MaintenanceVehicleRegister());
        menuMaintenanceVehicleCatalog.addActionListener(e -> new MaintenanceVehicleCatalog());
        menuMaintenanceVehicleSearch.addActionListener(e -> new MaintenanceVehicleSearch());
        // Parts menu
        menuPartsRegister.addActionListener(e -> new PartRegister());
        menuPartsCatalog.addActionListener(e -> new PartCatalog());
        menuPartsSearch.addActionListener(e -> new PartSearch());
        // Employee menu
//        menuEmployeeRegister.addActionListener(e -> new EmployeeRegister());
//        menuEmployeeCatalog.addActionListener(e -> new EmployeeCatalog());
//        menuEmployeeSearch.addActionListener(e -> new EmployeeSearch());
//        // Department menu
//        menuDepartmentRegister.addActionListener(e -> new DepartmentRegister());
//        menuDepartmentCatalog.addActionListener(e -> new DepartmentCatalog());
//        menuDepartmentSearch.addActionListener(e -> new DepartmentSearch());
        // Client menu
        menuClientRegister.addActionListener(e -> new ClientRegister());
        menuClientCatalog.addActionListener(e -> new ClientCatalog());
        menuClientSearch.addActionListener(e -> new ClientSearch());
        // Model menu
//        menuModelRegister.addActionListener(e -> new ModelRegister());
//        menuModelCatalog.addActionListener(e -> new ModelCatalog());
//        menuModelSearch.addActionListener(e -> new ModelSearch());
        // Provider menu
        menuProviderRegister.addActionListener(e -> new ProviderRegister());
        menuProviderCatalog.addActionListener(e -> new ProviderCatalog());
        menuProviderSearch.addActionListener(e -> new ProviderSearch());

        // Set menu bar to this frame
        setJMenuBar(menuBar);
    }
}
