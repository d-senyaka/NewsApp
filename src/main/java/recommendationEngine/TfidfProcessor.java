package recommendationEngine;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TfidfProcessor {

    public List<Vector> computeTfIdf(String datasetPath) throws Exception {
        System.out.println("Reading dataset from: " + datasetPath);

        // Read the dataset
        List<String> articles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(datasetPath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length < 4 || fields[1].isEmpty() || fields[2].isEmpty() || fields[3].isEmpty()) {
                    System.out.println("Skipping malformed or duplicate line: " + line);
                    continue;
                }
                String combinedText = fields[1] + " " + fields[2] + " " + fields[3];
                articles.add(combinedText);
            }

        }

        System.out.println("Articles after TF-IDF (with duplications as well): " + articles.size());

        // Compute term frequencies (TF)
        List<Map<String, Integer>> termFrequencies = new ArrayList<>();
        Map<String, Integer> globalFrequencies = new HashMap<>();
        for (String article : articles) {
            Map<String, Integer> tf = new HashMap<>();
            for (String word : article.split("\\s+")) {
                tf.put(word, tf.getOrDefault(word, 0) + 1);
                globalFrequencies.put(word, globalFrequencies.getOrDefault(word, 0) + 1);
            }
            termFrequencies.add(tf);
        }

        // Compute inverse document frequencies (IDF)
        Map<String, Double> idf = new HashMap<>();
        int totalDocs = articles.size();
        for (Map.Entry<String, Integer> entry : globalFrequencies.entrySet()) {
            idf.put(entry.getKey(), Math.log((double) totalDocs / (1 + entry.getValue())));
        }

        // Compute TF-IDF vectors
        List<Vector> tfidfVectors = new ArrayList<>();
        for (Map<String, Integer> tf : termFrequencies) {
            Vector vector = new DenseVector(idf.size());
            int i = 0;
            for (String term : idf.keySet()) {
                double tfidf = tf.getOrDefault(term, 0) * idf.get(term);
                vector.set(i++, tfidf);
            }
            tfidfVectors.add(vector);
        }

        return tfidfVectors;
    }
}
