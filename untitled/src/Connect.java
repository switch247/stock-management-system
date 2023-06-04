import java.sql.*;

public class Connect {
    private Connection conn;

    public Connect() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/MyStocks", "root", "");
//            System.out.printf("working");
        } catch (ClassNotFoundException ex) {
            System.out.println("Could not load database driver: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Could not connect to database: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not close database connection: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Connect c = new Connect();
    }
}