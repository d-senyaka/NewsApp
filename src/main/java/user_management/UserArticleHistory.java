package user_management;

import article_categorization.Article;
import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserArticleHistory {

    /**
     * Fetches article data and actions based on user preferences.
     *
     * @param userId The ID of the user.
     * @return A list of article-action pairs.
     */
    public List<ArticleActionForHistory> getArticlesWithActions(int userId) {
        List<ArticleActionForHistory> articlesWithActions = new ArrayList<>();

        String query = "SELECT a.id, a.title, a.description, a.urlToImage, a.publishedAt, " +
                "a.source_name, a.author, a.url, a.content, a.category, up.action " +
                "FROM article_table_c a " +
                "JOIN user_preferences up ON a.id = up.article_id " +
                "WHERE up.user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Article article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("urlToImage"),
                            rs.getString("publishedAt"),
                            rs.getString("source_name"),
                            rs.getString("author"),
                            rs.getString("url"),
                            rs.getString("content"),
                            rs.getString("category")
                    );
                    String action = rs.getString("action");
                    articlesWithActions.add(new ArticleActionForHistory(article, action));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching articles with actions: " + e.getMessage());
            e.printStackTrace();
        }

        return articlesWithActions;
    }
}
