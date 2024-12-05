package article_recommendations;

import article_categorization.Article;

import java.util.List;

public class RecommendationService {

    /**
     * Retrieves recommended articles for the given user ID.
     *
     * @param userId The ID of the user for whom to retrieve recommendations.
     * @return A list of recommended articles.
     */
    public List<Article> getRecommendedArticles(int userId) {
        return ArticleMapper.getArticlesFromRecommendations(userId);
    }

    /**
     * Filters recommended articles by category.
     *
     * @param articles The list of articles to filter.
     * @param category The category to filter by.
     * @return A filtered list of articles in the given category.
     */
    public List<Article> filterArticlesByCategory(List<Article> articles, String category) {
        if (category.equalsIgnoreCase("All")) {
            return articles;
        }

        return articles.stream()
                .filter(article -> article.getCategory().equalsIgnoreCase(category))
                .toList();
    }
}
