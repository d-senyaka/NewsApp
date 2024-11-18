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
