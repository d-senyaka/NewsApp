package org.example.API;

public class ArticleAPI {
    private final String title;
    private final String description;
    private final String url;
    private final String imageUrl;
    private final String publishedAt; // ISO 8601 datetime string
    private final String source;
    private final String author;
    private final String content;
    private final String category;

    public ArticleAPI(String title, String description, String url, String imageUrl, String publishedAt, String source, String author, String content, String category) {
        if (title == null || title.isEmpty()) throw new IllegalArgumentException("Title cannot be empty.");
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.source = source;
        this.author = author;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }
}
