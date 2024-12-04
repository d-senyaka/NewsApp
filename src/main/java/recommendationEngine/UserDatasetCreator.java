package recommendationEngine;

import classes.DatabaseConnector;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDatasetCreator {

    // Directory for datasets
    private static final String DATASET_DIRECTORY = "datasets";

    // Fetch articles and user preferences
    public List<ArticlePreference> fetchAllArticlesWithPreferences(int userId) {
        List<ArticlePreference> articles = new ArrayList<>();
        String query = """
            
            SELECT DISTINCT a.id AS article_id, a.title, a.description, a.content, a.category,
                   MAX(CASE WHEN up.action = 'read' THEN 1 ELSE 0 END) AS read_flag,
                   MAX(CASE WHEN up.action = 'like' THEN 1 ELSE 0 END) AS like_flag,
                   MAX(CASE WHEN up.action = 'dislike' THEN 1 ELSE 0 END) AS dislike_flag
            FROM article_table_c a
            LEFT JOIN user_preferences up ON a.id = up.article_id AND up.user_id = ?
            GROUP BY a.id, a.title, a.description, a.content, a.category
            
            """;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (isValidRow(rs)) {
                    articles.add(createArticlePreference(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    private ArticlePreference createArticlePreference(ResultSet rs) throws SQLException {
        return new ArticlePreference(
                rs.getInt("article_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("content"),
                rs.getString("category"),
                rs.getInt("read_flag"),
                rs.getInt("like_flag"),
                rs.getInt("dislike_flag")
        );
    }

    private boolean isValidRow(ResultSet rs) throws SQLException {
        return !(rs.getString("title").isEmpty() || rs.getString("description").isEmpty() ||
                rs.getString("content").isEmpty() || rs.getString("title").equalsIgnoreCase("[Removed]") ||
                rs.getString("description").equalsIgnoreCase("[Removed]") ||
                rs.getString("content").equalsIgnoreCase("[Removed]"));
    }

    public void saveUserDataset(int userId, List<ArticlePreference> articles) {
        String filePath = getDatasetPath(userId);

        // Create the dataset directory if it doesn't exist
        createDatasetDirectory();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write CSV header
            writer.write("article_id,title,description,content,category,read_flag,like_flag,dislike_flag\n");

            for (ArticlePreference article : articles) {
                if (isValidArticle(article.getArticleId())) {
                    writer.write(formatArticleToCSV(article));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatArticleToCSV(ArticlePreference article) {
        return String.format(
                "%d,\"%s\",\"%s\",\"%s\",\"%s\",%d,%d,%d\n",
                article.getArticleId(),
                article.getTitle().replace("\"", "\"\""),
                article.getDescription().replace("\"", "\"\""),
                article.getContent().replace("\"", "\"\""),
                article.getCategory(),
                article.getReadFlag(),
                article.getLikeFlag(),
                article.getDislikeFlag()
        );
    }

    private void createDatasetDirectory() {
        File directory = new File(DATASET_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private boolean isValidArticle(int articleId) {
        String query = "SELECT id FROM article_table_c WHERE id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleId);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getDatasetPath(int userId) {
        return DATASET_DIRECTORY + "/user_" + userId + "_dataset.csv";
    }

    // Helper Class for Article Preferences
    public static class ArticlePreference {
        private final int articleId;
        private final String title;
        private final String description;
        private final String content;
        private final String category;
        private final int readFlag;
        private final int likeFlag;
        private final int dislikeFlag;

        public ArticlePreference(int articleId, String title, String description, String content, String category,
                                 int readFlag, int likeFlag, int dislikeFlag) {
            this.articleId = articleId;
            this.title = title;
            this.description = description;
            this.content = content;
            this.category = category;
            this.readFlag = readFlag;
            this.likeFlag = likeFlag;
            this.dislikeFlag = dislikeFlag;
        }

        public int getArticleId() {
            return articleId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getContent() {
            return content;
        }

        public String getCategory() {
            return category;
        }

        public int getReadFlag() {
            return readFlag;
        }

        public int getLikeFlag() {
            return likeFlag;
        }

        public int getDislikeFlag() {
            return dislikeFlag;
        }
    }
}
