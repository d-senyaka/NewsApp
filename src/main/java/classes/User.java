package classes;

public class User {
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password, String confirmPassword) {
        validateFields(username, email, password, confirmPassword);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private void validateFields(String username, String email, String password, String confirmPassword) {
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
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password; // This should be hashed before storing in a real application
    }
}
