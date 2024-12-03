package user_management;

import classes.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAccountUpdater {

    public void updateUserDetails(int userId, String newUsername, String newEmail, String newPassword) throws IllegalArgumentException, SQLException {
        // Validate inputs if provided
        if (newUsername != null && !newUsername.isBlank()) {
            validateUsername(newUsername);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            validateEmail(newEmail);
        }
        if (newPassword != null && !newPassword.isBlank()) {
            validatePassword(newPassword);
        }

        String query = "UPDATE users SET " +
                "username = COALESCE(?, username), " +
                "email = COALESCE(?, email), " +
                "password_hash = COALESCE(?, password_hash) " +
                "WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, (newUsername == null || newUsername.isBlank()) ? null : newUsername);
            stmt.setString(2, (newEmail == null || newEmail.isBlank()) ? null : newEmail);
            stmt.setString(3, (newPassword == null || newPassword.isBlank()) ? null : newPassword); // Hash in real implementation
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        }
    }

    private void validateUsername(String username) {
        if (username.length() < 3) {
            throw new IllegalArgumentException("New Username must be at least 3 characters long.");
        }
    }

    private void validateEmail(String email) {
        if (!email.contains("@") || !email.endsWith(".com")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("NewPassword must be at least 8 characters long.");
        }
    }
}
