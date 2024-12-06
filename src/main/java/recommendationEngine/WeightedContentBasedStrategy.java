package recommendationEngine;

import article_categorization.Article;
import user_action_handlers.UserPreference;

import java.util.*;

public class WeightedContentBasedStrategy implements RecommendationStrategy {

    private static final double LIKE_WEIGHT = 1.0;
    private static final double READ_WEIGHT = 0.5;
    private static final double DISLIKE_WEIGHT = -1.0;
    private static final double CATEGORY_BOOST = 0.1;

    @Override
    public List<Recommendation> recommend(List<Article> articles, int userId) {
        List<UserPreference.Preference> userPreferences = UserPreference.getUserPreferences(userId);
        Map<Integer, Double> articleScores = new HashMap<>();

        for (Article article : articles) {
            double score = 0.0;
            for (UserPreference.Preference preference : userPreferences) {
                if (preference.getArticleId() != article.getId()) {
                    double similarity = computeCosineSimilarity(article, preference.getArticle());
                    score += applyWeights(similarity, preference.getAction());
                }
            }

            // Apply category boost
            if (userPreferences.stream().anyMatch(pref -> pref.getCategory().equals(article.getCategory()))) {
                score += CATEGORY_BOOST;
            }

            articleScores.put(article.getId(), score);
        }

        // Sort articles by score
        return articleScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .map(entry -> new Recommendation(entry.getKey(), entry.getValue()))
                .toList();
    }

    private double computeCosineSimilarity(Article a1, Article a2) {
        Map<String, Double> vector1 = a1.getTfidfVector();
        Map<String, Double> vector2 = a2.getTfidfVector();
        double dotProduct = vector1.keySet().stream()
                .filter(vector2::containsKey)
                .mapToDouble(term -> vector1.get(term) * vector2.get(term))
                .sum();
        double magnitude1 = Math.sqrt(vector1.values().stream().mapToDouble(v -> v * v).sum());
        double magnitude2 = Math.sqrt(vector2.values().stream().mapToDouble(v -> v * v).sum());
        return dotProduct / (magnitude1 * magnitude2);
    }


    private double applyWeights(double similarity, String action) {
        return switch (action) {
            case "like" -> similarity * LIKE_WEIGHT;
            case "read" -> similarity * READ_WEIGHT;
            case "dislike" -> similarity * DISLIKE_WEIGHT;
            default -> 0.0;
        };
    }
}
