package org.example.api;

import classes.Article; // Updated to use `classes.Article`.
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsParser {

    /**
     * Parses the news articles from the provided JSON data.
     *
     * @param jsonData The raw JSON string containing article data.
     * @return A list of parsed `classes.Article` objects.
     */
    public List<Article> parseNews(String jsonData) {
        List<Article> articles = new ArrayList<>();
        JSONObject obj = new JSONObject(jsonData);
        JSONArray data = obj.getJSONArray("articles");

        for (int i = 0; i < data.length(); i++) {
            JSONObject articleJson = data.getJSONObject(i);

            // Parse article fields
            String title = articleJson.optString("title", "Untitled");
            String description = articleJson.optString("description", "No description available");
            String imageUrl = articleJson.optString("urlToImage", "");
            String publishedAt = articleJson.optString("publishedAt", "Unknown date");
            String source = articleJson.getJSONObject("source").optString("name", "Unknown source");
            String author = articleJson.optString("author", "Anonymous");
            String url = articleJson.optString("url", "");
            String content = articleJson.optString("content", "No content available");

            try {
                // Use `classes.Article`
                String category = "Uncategorized"; // Default category
                Article article = new Article(title, description, url, imageUrl, publishedAt, source, author, content, category);
                articles.add(article);
            } catch (IllegalArgumentException e) {
                // Handle validation failures in Article constructor
                System.out.println("Skipping invalid article: " + e.getMessage());
            }
        }

        System.out.println("Total articles parsed: " + articles.size());
        return articles;
    }
}
