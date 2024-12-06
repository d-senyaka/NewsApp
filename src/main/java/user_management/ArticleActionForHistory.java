package user_management;

import article_categorization.Article;

public class ArticleActionForHistory {
    private final Article article;
    private final String action;

    public ArticleActionForHistory(Article article, String action) {
        this.article = article;
        this.action = action;
    }

    public Article getArticle() {
        return article;
    }

    public String getAction() {
        return action;
    }
}
