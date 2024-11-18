package classes;

public class Article {
    private String title;
    private String description;
    private String imageUrl;
    private String publishedAt;
    private String source;
    private String author;
    private String url;
    private String content;

    public Article(String title, String description, String imageUrl, String publishedAt, String source, String author, String url, String content) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.source = source;
        this.author = author;
        this.url = url;
        this.content = content;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getPublishedAt() { return publishedAt; }
    public String getSource() { return source; }
    public String getAuthor() { return author; }
    public String getUrl() { return url; }
    public String getContent() { return content; }
}
