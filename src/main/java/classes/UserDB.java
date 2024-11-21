package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDB {

    // Method to save user data to the database
    public void saveUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, 'user')";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // Consider hashing this password in a real application

            // Execute the query
            stmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
            throw e; // Re-throw the exception to handle it in the calling code if needed
        }
    }
}
