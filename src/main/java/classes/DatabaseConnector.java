package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/-";
    private static final String USER = "-";
    private static final String PASSWORD = "-";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Suppress the stack trace and display a simple message
            System.out.println("Database connection unavailable. Please ensure the MySQL server is running.");
            return null; // Return null to indicate failure
        }
    }
}
