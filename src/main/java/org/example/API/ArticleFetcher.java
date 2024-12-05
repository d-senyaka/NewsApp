package org.example.API;

import classes.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleFetcher {

    private final NewsService newsService = new NewsService();
    private final NewsParser newsParser = new NewsParser(); // Updated to return `classes.Article`.
    private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Thread pool for concurrency

    //for concurrency
    public void fetchAndProcessArticlesAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Fetching articles asynchronously...");
                fetchAndProcessArticles(); // The original method
                System.out.println("Articles fetched and processed successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error during asynchronous fetching: " + e.getMessage());
            }
        }, executorService);
        shutdown();
    }

    public void shutdown() {
        executorService.shutdown();
        System.out.println("ExecutorService shut down.");
    }

    public void fetchAndProcessArticles() throws Exception {
        // Step 1: Fetch news data from the API
        String jsonData = newsService.fetchNewsData();

        // Step 2: Parse the JSON data into articles
        List<ArticleAPI> articleAPIS = newsParser.parseNews(jsonData); // Use `classes.Article` objects here.

        // Step 3: Insert articles into article_table_a
        insertArticlesIntoTableA(articleAPIS);

        // Step 4: Transfer unique articles from table_a to table_d
        try (Connection conn = DatabaseConnector.getConnection()) {
            transferArticlesToD(conn);
        }
    }

    private void insertArticlesIntoTableA(List<ArticleAPI> articleAPIS) {
        String query = "INSERT INTO article_table_a (title, description, url, urlToImage, publishedAt, source_name, author, content, category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (ArticleAPI articleAPI : articleAPIS) {
                stmt.setString(1, articleAPI.getTitle());
                stmt.setString(2, articleAPI.getDescription());
                stmt.setString(3, articleAPI.getUrl());
                stmt.setString(4, articleAPI.getImageUrl());
                stmt.setString(5, formatDateTime(articleAPI.getPublishedAt())); // Format datetime
                stmt.setString(6, articleAPI.getSource());
                stmt.setString(7, articleAPI.getAuthor());
                stmt.setString(8, articleAPI.getContent());
                stmt.setString(9, articleAPI.getCategory());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error inserting articles into article_table_a: " + e.getMessage());
        }
    }

    private void transferArticlesToD(Connection conn) {
        String query = "INSERT INTO article_table_d (title, description, url, urlToImage, publishedAt, source_name, author, content, category) " +
                "SELECT a.title, a.description, a.url, a.urlToImage, a.publishedAt, a.source_name, a.author, a.content, a.category " +
                "FROM article_table_a a WHERE NOT EXISTS (SELECT 1 FROM article_table_d d WHERE d.title = a.title AND d.publishedAt = a.publishedAt)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Transferred " + rowsInserted + " unique articles to article_table_d.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error transferring articles to article_table_d: " + e.getMessage());
        }
    }

    private String formatDateTime(String isoDateTime) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return mysqlFormat.format(isoFormat.parse(isoDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error formatting datetime: " + isoDateTime);
            return null; // Return null if formatting fails
        }
    }
}
