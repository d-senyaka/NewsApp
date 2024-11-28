package article_categorization;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Concrete Strategy for Fetching All Articles
public class FetchAllArticlesStrategy implements article_categorization.ArticleFetchStrategy {
    @Override
    public List<article_categorization.Article> fetchArticles(Connection conn) throws Exception {
        List<article_categorization.Article> articles = new ArrayList<>();
        String query = "SELECT id, title, description, urlToImage, publishedAt, source_name, author, url, content, category FROM article_table_d";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                articles.add(new article_categorization.Article(
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
                ));
            }
        }
        return articles;
    }
}
