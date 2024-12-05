package article_recommendations;

import article_categorization.Article;
import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleManagerForRecs {
    public static Article getArticleById(int articleId) {
        String query = "SELECT * FROM article_table_c WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("content"),
                        rs.getString("category"),
                        rs.getString("imageUrl"), // Replace with actual column name
                        rs.getString("publishedAt"), // Replace with actual column name
                        rs.getString("source"), // Replace with actual column name
                        rs.getString("author") // Replace with actual column name
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no article is found
    }
}

