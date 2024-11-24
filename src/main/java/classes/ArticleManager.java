package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    public List<Article> getArticlesFromDatabase() {
        List<Article> articles = new ArrayList<>();

        Connection conn = DatabaseConnector.getConnection();
        if (conn == null) {
            // Log a simple message and return an empty list if the connection is null
            System.out.println("Database connection failed. Unable to fetch articles.");
            return articles; // Return an empty list instead of null to prevent NullPointerException
        }

        try (Statement stmt = conn.createStatement()) {
            // Ensure the query includes the id and category columns
            String query = "SELECT id, title, description, urlToImage, publishedAt, source_name, author, url, content, category FROM article_table_d";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id"); // Fetch the article ID
                String title = rs.getString("title");
                String description = rs.getString("description");
                String imageUrl = rs.getString("urlToImage");
                String publishedAt = rs.getString("publishedAt");
                String source = rs.getString("source_name");
                String author = rs.getString("author");
                String url = rs.getString("url");
                String content = rs.getString("content");
                String category = rs.getString("category"); // Fetch the category

                // Pass all required fields to the Article constructor
                articles.add(new Article(id, title, description, imageUrl, publishedAt, source, author, url, content, category));
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching articles from the database: " + e.getMessage());
        }

        return articles;
    }
}
