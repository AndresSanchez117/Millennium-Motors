import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection getConnection() {
        Connection conn;
        try {
            String url = "jdbc:sqlite:/home/andres/Ing/5-IngenieriaDeSoftware/MileniumProject/milenium.db";
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            conn = null;
        }
        return conn;
    }
}
