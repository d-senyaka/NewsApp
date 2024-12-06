package recommendationEngine;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class UserDatasetCreatorTest {

    @Test
    public void testDatasetCreation() {
        int userId = 1; // Example user ID
        UserDatasetCreator datasetCreator = new UserDatasetCreator();
        List<UserDatasetCreator.ArticlePreference> preferences = datasetCreator.fetchAllArticlesWithPreferences(userId);

        // Assert that preferences are fetched
        assertNotNull(preferences);
        assertFalse(preferences.isEmpty());

        // Save the dataset
        datasetCreator.saveUserDataset(userId, preferences);

        // Verify the dataset file exists
        File datasetFile = new File("datasets/user_" + userId + "_dataset.csv");
        assertTrue(datasetFile.exists());
    }

}
