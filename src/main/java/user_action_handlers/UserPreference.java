package user_action_handlers;

import article_categorization.Article;
import article_recommendations.ArticleManagerForRecs;
import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Centralized class for managing user preferences
public class UserPreference {

    // Save user action to the database
    public static void saveUserAction(int userId, int articleId, String category, String action) {
        if (isPreferenceAlreadyExists(userId, articleId, action)) {
            System.out.println("Action already exists: UserID=" + userId + ", ArticleID=" + articleId + ", Action=" + action);
            return;
        }

        String query = "INSERT INTO user_preferences (user_id, article_id, category, action) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, articleId);
            stmt.setString(3, category);
            stmt.setString(4, action);

            stmt.executeUpdate();
            System.out.println("User action saved: UserID=" + userId + ", ArticleID=" + articleId + ", Action=" + action);
        } catch (SQLException e) {
            System.err.println("Error saving user action: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Check if a preference already exists
    private static boolean isPreferenceAlreadyExists(int userId, int articleId, String action) {
        String query = "SELECT COUNT(*) FROM user_preferences WHERE user_id = ? AND article_id = ? AND action = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, articleId);
            stmt.setString(3, action);

            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0; // Record exists if count > 0
        } catch (SQLException e) {
            System.err.println("Error checking user preference: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static class Preference {
        private final int articleId;
        private final Article article;
        private final String action;
        private final String category;

        public Preference(int articleId, Article article, String action, String category) {
            this.articleId = articleId;
            this.article = article;
            this.action = action;
            this.category = category;
        }

        public int getArticleId() {
            return articleId;
        }

        public Article getArticle() {
            return article;
        }

        public String getAction() {
            return action;
        }

        public String getCategory() {
            return category;
        }
    }

    public static List<Preference> getUserPreferences(int userId) {
        List<Preference> preferences = new ArrayList<>();
        String query = "SELECT article_id, action, category FROM user_preferences WHERE user_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int articleId = rs.getInt("article_id");
                String action = rs.getString("action");
                String category = rs.getString("category");
                Article article = ArticleManagerForRecs.getArticleById(articleId);
                preferences.add(new Preference(articleId, article, action, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preferences;
    }

}
