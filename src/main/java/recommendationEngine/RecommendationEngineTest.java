package recommendationEngine;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class RecommendationEngineTest {

    @Test
    public void testRecommendationGeneration() {
        RecommendationEngine engine = new RecommendationEngine(new WeightedContentBasedStrategy());
        UserDatasetCreator datasetCreator = new UserDatasetCreator();

        // Generate a dataset for user 1 for testing purposes
        int testUserId = 3;
        List<UserDatasetCreator.ArticlePreference> preferences = datasetCreator.fetchAllArticlesWithPreferences(testUserId);
        datasetCreator.saveUserDataset(testUserId, preferences);

        // Test recommendation generation
        engine.generateRecommendationsAsync();
        System.out.println("RecommendationEngineTest: Recommendation generation started. Check logs for details.");
    }


    @Test
    public void testInteractedArticlesFetch() {
        RecommendationEngine engine = new RecommendationEngine(new WeightedContentBasedStrategy());
        List<Integer> interactedArticles = engine.getInteractedArticleIds(3); // Example user ID

        assertNotNull("Interacted articles should not be null.", interactedArticles);
        System.out.println("RecommendationEngineTest: Interacted articles for user 3: " + interactedArticles);
    }
}
