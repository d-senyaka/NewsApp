package recommendationEngine;



public class RecommendedArticle {
    private int articleId;
    private String title;
    private String category;
    private double score; // Recommendation score

    // Constructor
    public RecommendedArticle(int articleId, String title, String category, double score) {
        this.articleId = articleId;
        this.title = title;
        this.category = category;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RecommendedArticle{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", score=" + score +
                '}';
    }
}

