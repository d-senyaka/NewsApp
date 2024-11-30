package user_management;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Polymorphic Interface for Users
interface IUser {
    String getUsername();
    String getEmail();
}

// Concrete Implementation of Regular User
public class User implements IUser {
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password, String confirmPassword) {
        UserValidator.validate(username, email, password, confirmPassword);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password; // Note: Passwords should be hashed before storing
    }
}

// Specialized Admin User Class
class AdminUser extends User {
    private String adminCode;

    public AdminUser(String username, String email, String password, String confirmPassword, String adminCode) {
        super(username, email, password, confirmPassword);
        this.adminCode = adminCode;
    }

    public String getAdminCode() {
        return adminCode;
    }
}

// Utility Class for Validation
class UserValidator {
    public static void validate(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        if (!email.contains("@") || !email.endsWith(".com")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        // Check database for existing username and email
        if (isUsernameInUse(username)) {
            throw new IllegalArgumentException("Username is already in use. Use another.");
        }
        if (isEmailInUse(email)) {
            throw new IllegalArgumentException("Email is already in use. Use another.");
        }
    }

    private static boolean isUsernameInUse(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Database error while checking username.");
        }
        return false;
    }

    private static boolean isEmailInUse(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Database error while checking email.");
        }
        return false;
    }
}
