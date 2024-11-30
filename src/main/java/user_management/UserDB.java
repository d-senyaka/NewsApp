package user_management;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    // Method to save user data to the database
    public int saveUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, 'user')";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the prepared statement
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // Consider hashing this password in a real application

            // Execute the query
            stmt.executeUpdate();

            // Retrieve the generated user ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1); // Get the first generated key (auto-incremented ID)
                    System.out.println("User registered successfully with ID: " + userId);
                    return userId;
                } else {
                    throw new SQLException("User ID retrieval failed.");
                }
            }

        } catch (SQLException e) {
            // Handle duplicate entry error specifically
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("for key 'email'")) {
                    throw new IllegalArgumentException("The email is already in use. Please use another email.");
                } else if (e.getMessage().contains("for key 'username'")) {
                    throw new IllegalArgumentException("The username is already in use. Please choose another username.");
                }
            }

            System.err.println("Error inserting user: " + e.getMessage());
            throw e; // Re-throw other SQL exceptions
        }
    }
}
