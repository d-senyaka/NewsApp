package recommendationEngine;

//passing recommendation information.

public class Recommendation {
    private int articleId;
    private double similarityScore;

    public Recommendation(int articleId, double similarityScore) {
        this.articleId = articleId;
        this.similarityScore = similarityScore;
    }

    public int getArticleId() {
        return articleId;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }
}
