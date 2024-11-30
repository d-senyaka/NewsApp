package classes;
import java.io.File;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private String username;
    private String email;

    private UserSession() {
        // Private constructor
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
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

    public void clearSession() {
        userId = 0;
        username = null;
        email = null;
    }
}
