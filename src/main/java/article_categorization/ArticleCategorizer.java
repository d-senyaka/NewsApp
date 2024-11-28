package article_categorization;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Strategy Interface for Categorization
interface CategoryStrategy {
    String determineCategory(String title, String description, String content);
}

public class ArticleCategorizer {
    private CategoryStrategy categoryStrategy;

    public ArticleCategorizer() {
        this.categoryStrategy = new KeywordCategoryStrategy(); // Default strategy
    }

    public void setCategoryStrategy(CategoryStrategy strategy) {
        this.categoryStrategy = strategy;
    }

    public void categorizeAndStoreArticles() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String fetchQuery = "SELECT * FROM article_table_d";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(fetchQuery);

            String insertQuery = "INSERT INTO article_table_c (id, title, description, urlToImage, publishedAt, source_name, author, url, content, category) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            String checkQuery = "SELECT COUNT(*) FROM article_table_c WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String content = rs.getString("content");
                String originalCategory = rs.getString("category");

                if (title == null || rs.getString("publishedAt") == null) continue;

                checkStmt.setInt(1, id);
                ResultSet checkResult = checkStmt.executeQuery();
                if (checkResult.next() && checkResult.getInt(1) > 0) continue;

                String determinedCategory = categoryStrategy.determineCategory(
                        title != null ? title : "",
                        description != null ? description : "",
                        content != null ? content : ""
                );

                String finalCategory = (originalCategory != null && !originalCategory.isBlank())
                        ? originalCategory
                        : determinedCategory;

                insertStmt.setInt(1, id);
                insertStmt.setString(2, title);
                insertStmt.setString(3, description);
                insertStmt.setString(4, rs.getString("urlToImage"));
                insertStmt.setString(5, rs.getString("publishedAt"));
                insertStmt.setString(6, rs.getString("source_name"));
                insertStmt.setString(7, rs.getString("author"));
                insertStmt.setString(8, rs.getString("url"));
                insertStmt.setString(9, content);
                insertStmt.setString(10, finalCategory);

                insertStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<article_categorization.Article> getArticlesByCategory(String category) {
        List<article_categorization.Article> articles = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM article_table_c WHERE category = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, category);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articles.add(new article_categorization.Article(
                        rs.getInt("id"), // id
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("urlToImage"),
                        rs.getString("publishedAt"),
                        rs.getString("source_name"),
                        rs.getString("author"),
                        rs.getString("url"),
                        rs.getString("content"),
                        rs.getString("category") // category
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<article_categorization.Article> getAllArticles() {
        List<article_categorization.Article> articles = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM article_table_c";
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                articles.add(new article_categorization.Article(
                        rs.getInt("id"), // id
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("urlToImage"),
                        rs.getString("publishedAt"),
                        rs.getString("source_name"),
                        rs.getString("author"),
                        rs.getString("url"),
                        rs.getString("content"),
                        rs.getString("category") // category
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}
