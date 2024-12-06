package user_management;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminManageUser {

    public static List<UserDataForAdmin> fetchAllUsers() {
        List<UserDataForAdmin> users = new ArrayList<>();
        String query = "SELECT user_id, username, email FROM users";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String email = rs.getString("email");

                users.add(new UserDataForAdmin(userId, username, email)); // Use UserSummary here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public static boolean deleteUser(int userId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Delete dependent records in user_preferences
            String deletePreferencesQuery = "DELETE FROM user_preferences WHERE user_id = ?";
            try (PreparedStatement deletePreferencesStmt = conn.prepareStatement(deletePreferencesQuery)) {
                deletePreferencesStmt.setInt(1, userId);
                deletePreferencesStmt.executeUpdate();
            }

            // Delete the user
            String deleteUserQuery = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserQuery)) {
                deleteUserStmt.setInt(1, userId);
                deleteUserStmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
