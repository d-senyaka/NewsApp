package controllers;

import javafx.scene.image.Image;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageCache {
    // In-memory cache for storing images
    private final Map<String, Image> cache = new ConcurrentHashMap<>();

    // Singleton instance of ImageCache
    private static final ImageCache instance = new ImageCache();

    // Private constructor to ensure singleton
    private ImageCache() {}

    // Get the singleton instance
    public static ImageCache getInstance() {
        return instance;
    }

    /**
     * Retrieves an image from the cache or loads it if not present.
     *
     * @param url The URL of the image.
     * @return The Image object.
     */
    public Image getImage(String url) {
        // Check if the image is already cached
        if (cache.containsKey(url)) {
            return cache.get(url);
        }

        // If not cached, load the image and store it in the cache
        try {
            Image image = new Image(url, true); // Use true to load the image asynchronously
            cache.put(url, image);
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load image from URL: " + url);
            throw new IllegalArgumentException("Failed to load image from URL: " + url, e);
        }
    }

    /**
     * Clears the cache.
     */
    public void clearCache() {
        cache.clear();
    }
}
