package classes;

import classes.DatabaseConnector;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.*;

public class UserLogin {

    public String authenticate(String usernameOrEmail, String password) {
        // Check if the username/email or password is empty
        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            if (usernameOrEmail.isEmpty()) {
                return "Please enter your username or email."; // If username/email is empty
            }
            if (password.isEmpty()) {
                return "Please enter the password."; // If password is empty
            }
        }

        String query = "SELECT username, email, password_hash FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return "Incorrect username or email."; // No user found
            }

            String dbPasswordHash = rs.getString("password_hash");

            // Password mismatch handling
            if (!verifyPassword(password, dbPasswordHash)) {
                return "The password you entered is incorrect.";
            }

            return null; // Successful authentication

        } catch (CommunicationsException e) {
            // Database connection failure (e.g., MySQL server not running)
            System.err.println("Database connection error: " + e.getMessage());
            return "Database failure. Please try again later."; // Return error message for the controller
        } catch (SQLException e) {
            // Handle other SQL exceptions
            System.err.println("SQL error: " + e.getMessage());
            return "Database error. Please try again later.";
        }
    }

    private boolean verifyPassword(String enteredPassword, String dbPasswordHash) {
        // In a real application, you would use a secure password hash verification method, like BCrypt
        return enteredPassword.equals(dbPasswordHash);
    }
}
