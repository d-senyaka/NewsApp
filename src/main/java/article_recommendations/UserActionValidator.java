package article_recommendations;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserActionValidator {

    private static final int MIN_ACTIONS_REQUIRED = 10;

    /**
     * Checks how many actions the user has in the `user_preferences` table.
     *
     * @param userId The user's ID.
     * @return The number of actions the user has performed.
     */
    public int getUserActionCount(int userId) {
        String query = "SELECT COUNT(*) FROM user_preferences WHERE user_id = ?";
        int actionCount = 0;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                actionCount = rs.getInt(1);
            }

        } catch (Exception e) {
            System.err.println("Error checking user actions: " + e.getMessage());
            e.printStackTrace();
        }

        return actionCount;
    }

    /**
     * Checks if the user has performed enough actions to proceed.
     *
     * @param userId The user's ID.
     * @return The number of missing actions, or 0 if the user meets the requirement.
     */
    public int getMissingActions(int userId) {
        int userActionCount = getUserActionCount(userId);
        return Math.max(0, MIN_ACTIONS_REQUIRED - userActionCount);
    }
}
