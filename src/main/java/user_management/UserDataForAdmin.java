package user_management;

public class UserDataForAdmin {
    private int userId;
    private String username;
    private String email;

    public UserDataForAdmin(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
