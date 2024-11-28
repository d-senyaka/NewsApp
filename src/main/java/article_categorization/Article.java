package article_categorization;

// Concrete implementation of the interface
public class Article implements InterfaceArticle {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String publishedAt;
    private String source;
    private String author;
    private String url;
    private String content;
    private String category;

    // Constructor with validation
    public Article(int id, String title, String description, String imageUrl, String publishedAt,
                   String source, String author, String url, String content, String category) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be null or empty.");
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.source = source;
        this.author = author;
        this.url = url;
        this.content = content;
        this.category = category;
    }

    // Overloaded constructor for cases without category
    public Article(int id, String title, String description, String imageUrl, String publishedAt,
                   String source, String author, String url, String content) {
        this(id, title, description, imageUrl, publishedAt, source, author, url, content, "Uncategorized");
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    // Overridden toString for better debugging
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

    // Overridden equals and hashCode for comparing objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
