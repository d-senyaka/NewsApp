package recommendationEngine;

import classes.DatabaseConnector;
import classes.UserSession;
import org.apache.mahout.math.Vector;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecommendationEngine {

    private RecommendationStrategy strategy;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Thread pool for concurrency

    public RecommendationEngine(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public void generateRecommendationsAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Generating recommendations asynchronously...");
                processRecommendations();
                System.out.println("Recommendations generated successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error generating recommendations: " + e.getMessage());
            }
        }, executorService);
        shutdown();
    }

    public void shutdown() {
        executorService.shutdown();
        System.out.println("ExecutorService shut down.");
    }


    public void processRecommendations() {
        try {
            int userId = UserSession.getInstance().getUserId();
            String datasetPath = DatasetLoader.getDatasetPath(userId);

            TfidfProcessor processor = new TfidfProcessor();
            List<Vector> tfidfVectors = processor.computeTfIdf(datasetPath);

            List<Integer> interactedArticles = getInteractedArticleIds(userId);
            Set<Integer> uniqueInteractedArticles = new HashSet<>(interactedArticles);

            List<Recommendation> recommendations = computeTopRecommendations(tfidfVectors, uniqueInteractedArticles, 15);

            saveRecommendationsToDatabase(userId, recommendations);

            logRecommendations(recommendations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getInteractedArticleIds(int userId) {
        List<Integer> articleIds = new ArrayList<>();
        String query = "SELECT DISTINCT article_id FROM user_preferences WHERE user_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articleIds.add(rs.getInt("article_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleIds;
    }

    private List<Recommendation> computeTopRecommendations(List<Vector> tfidfVectors, Set<Integer> interactedArticles, int topN) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (int i = 0; i < tfidfVectors.size(); i++) {
            if (!interactedArticles.contains(i)) {
                for (int j : interactedArticles) {
                    double similarity = computeCosineSimilarity(tfidfVectors.get(i), tfidfVectors.get(j));
                    recommendations.add(new Recommendation(i, similarity));
                }
            }
        }
        recommendations.sort((r1, r2) -> Double.compare(r2.getSimilarityScore(), r1.getSimilarityScore()));
        return recommendations.subList(0, Math.min(topN, recommendations.size()));
    }

    private double computeCosineSimilarity(Vector vec1, Vector vec2) {
        return vec1.dot(vec2) / (vec1.norm(2) * vec2.norm(2));
    }

    private void logRecommendations(List<Recommendation> recommendations) {
        for (Recommendation recommendation : recommendations) {
            System.out.println("Recommended Article ID: " + recommendation.getArticleId() +
                    " | Similarity Score: " + recommendation.getSimilarityScore());
        }
    }

    private void saveRecommendationsToDatabase(int userId, List<Recommendation> recommendations) {
        String query = "INSERT INTO recommended_articles (user_id, article_id, score) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (Recommendation recommendation : recommendations) {
                // Check if the article ID exists in the database (optional)
                if (!isValidArticleId(recommendation.getArticleId())) {
                    System.err.println("Skipping invalid article ID: " + recommendation.getArticleId());
                    continue; // Skip invalid articles
                }

                stmt.setInt(1, userId);
                stmt.setInt(2, recommendation.getArticleId());
                stmt.setDouble(3, recommendation.getSimilarityScore());

                try {
                    stmt.executeUpdate(); // Execute the insert for each recommendation
                    System.out.println("Saved recommendation for article_id=" + recommendation.getArticleId());
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.err.println("Duplicate recommendation for article_id=" + recommendation.getArticleId());
                    // Optionally log this for debugging purposes
                }
            }

            System.out.println("All recommendations processed.");
        } catch (SQLException e) {
            System.err.println("Error saving recommendations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to validate article ID
    private boolean isValidArticleId(int articleId) {
        String query = "SELECT id FROM article_table_c WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, articleId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Return true if the article ID exists
        } catch (SQLException e) {
            System.err.println("Error validating article ID: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
