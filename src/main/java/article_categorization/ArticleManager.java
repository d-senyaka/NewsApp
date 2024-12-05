package article_categorization;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

// Strategy Interface for Fetching Articles
interface ArticleFetchStrategy {
    List<Article> fetchArticles(Connection conn) throws Exception;
}

public class ArticleManager {
    private ArticleFetchStrategy fetchStrategy;

    public ArticleManager() {
        this.fetchStrategy = new GetUncatArticles(); // Default strategy
    }

    public void setFetchStrategy(ArticleFetchStrategy strategy) {
        this.fetchStrategy = strategy;
    }

    public List<Article> getArticlesFromDatabase() {
        List<Article> articles = new ArrayList<>();
        Connection conn = DatabaseConnector.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed. Unable to fetch articles.");
            return articles;
        }

        try {
            articles = fetchStrategy.fetchArticles(conn);
        } catch (Exception e) {
            System.out.println("Error occurred while fetching articles: " + e.getMessage());
        }

        return articles;
    }



}
