package user_management;

public class AdminUser {
    private static final String ADMIN_NAME = "Admin";
    private static final String ADMIN_PASSWORD = "log";

    private String username;
    private String password;

    // Constructor to create an AdminUser instance
    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter for admin username
    public String getUsername() {
        return username;
    }

    // Getter for admin password (use cautiously)
    public String getPassword() {
        return password;
    }

    // Method to authenticate admin credentials
    public static boolean authenticate(String adminName, String password) {
        return ADMIN_NAME.equals(adminName) && ADMIN_PASSWORD.equals(password);
    }

    // Utility to check if an AdminUser instance is valid
    public boolean isValidAdmin() {
        return ADMIN_NAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
