package recommendationEngine;

import article_categorization.Article;

import java.util.List;

public interface RecommendationStrategy {
    List<Recommendation> recommend(List<Article> articles, int userId);
}
