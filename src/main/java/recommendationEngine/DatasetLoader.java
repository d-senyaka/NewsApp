package recommendationEngine;

import org.junit.Test;

import java.io.File;

import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatasetLoader {
    private static final Logger logger = Logger.getLogger(DatasetLoader.class.getName());

    public static String getDatasetPath(int userId) {
        String directoryPath = "datasets";
        String filePath = directoryPath + "/user_" + userId + "_dataset.csv";

        File datasetFile = new File(filePath);
        if (!datasetFile.exists()) {
            throw new RuntimeException("Dataset not found for user ID: " + userId);
        }

        System.out.println("Successfully loaded dataset for user ID: " + userId);
        return filePath;
    }


    @Test
    public void testDatasetLoader() {
        int userId = 2; // Example user ID
        String datasetPath = DatasetLoader.getDatasetPath(userId);

        // Assert the file path is correct
        assertNotNull(datasetPath);

        // Assert the file exists
        File datasetFile = new File(datasetPath);
        assertTrue(datasetFile.exists());
    }

    public void validateDatasetExists(int userId) {
        String datasetPath = getDatasetPath(userId);
        File file = new File(datasetPath);
        if (!file.exists()) {
            throw new RuntimeException("Dataset for user " + userId + " does not exist.");
        }
    }


}

