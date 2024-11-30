package user_management;

import classes.DatabaseConnector;
import classes.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Main authentication class
public class UserLogin {
    private final UserAuthenticator authenticator;

    // Constructor to use a specific authentication strategy (default is DatabaseAuthenticator)
    public UserLogin() {
        this.authenticator = new DatabaseAuthenticator();
    }

    public String authenticate(String usernameOrEmail, String password) {
        return authenticator.authenticate(usernameOrEmail, password);
    }
}

// Interface for authentication mechanisms
interface UserAuthenticator {
    String authenticate(String usernameOrEmail, String password);
}

// Database-based authentication implementation
class DatabaseAuthenticator implements UserAuthenticator {

    @Override
    public String authenticate(String usernameOrEmail, String password) {
        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            return usernameOrEmail.isEmpty() ? "Please enter your username or email." : "Please enter the password.";
        }

        try {
            // Fetch user data from the repository
            UserRepository repository = new UserRepository();
            UserData userData = repository.findUserByUsernameOrEmail(usernameOrEmail);

            if (userData == null) {
                return "Incorrect username or email.";
            }

            if (!PasswordUtils.verifyPassword(password, userData.getPasswordHash())) {
                return "The password you entered is incorrect.";
            }

            // Set user session
            UserSession.getInstance().setUser(userData.getUserId(), userData.getUsername(), userData.getEmail());
            return null; // Successful authentication

        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error. Please try again later.";
        }
    }
}

// Repository for user database operations
class UserRepository {

    public UserData findUserByUsernameOrEmail(String usernameOrEmail) throws SQLException {
        String query = "SELECT user_id, username, email, password_hash FROM users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserData(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash")
                );
            }
        }
        return null; // No user found
    }
}

// Utility class for password verification
class PasswordUtils {
    public static boolean verifyPassword(String enteredPassword, String dbPasswordHash) {
        // Replace this with actual hash verification logic (e.g., bcrypt)
        return enteredPassword.equals(dbPasswordHash);
    }
}

// Data Transfer Object (DTO) for user data
class UserData {
    private final int userId;
    private final String username;
    private final String email;
    private final String passwordHash;

    public UserData(int userId, String username, String email, String passwordHash) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
