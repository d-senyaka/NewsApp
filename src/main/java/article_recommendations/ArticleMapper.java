package article_recommendations;

import article_categorization.Article;
import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleMapper {

    public static List<Article> getArticlesFromRecommendations(int userId) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT a.id, a.title, a.description, a.content, a.urlToImage AS imageUrl, " +
                "a.publishedAt, a.source_name AS source, a.author, a.url, a.category " +
                "FROM recommended_articles r " +
                "JOIN article_table_c a ON r.article_id = a.id " +
                "WHERE r.user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("imageUrl"), // Map 'urlToImage' to 'imageUrl'
                        rs.getString("publishedAt"),
                        rs.getString("source"),
                        rs.getString("author"),
                        rs.getString("url"),
                        rs.getString("content"),
                        rs.getString("category")
                );
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
