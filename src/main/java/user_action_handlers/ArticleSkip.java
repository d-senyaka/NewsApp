package user_action_handlers;

import article_categorization.Article;
import classes.UserSession;
import javafx.scene.layout.VBox;

public class ArticleSkip implements ArticleActionHandler {

    @Override
    public void handle(Article article) {
        // Default implementation for `handle(Article)` as required by ArticleActionHandler
        System.out.println("Default handle for ArticleSkip called. Article: " + article.getTitle());
    }

    // Overloaded `handle` method to handle skipping articles
    public void handle(Article article, VBox articleBox, VBox articlesContainer) {
        int userId = UserSession.getInstance().getUserId();
        if (userId == 0) {
            System.out.println("No user logged in. Action not saved.");
            return;
        }

        UserPreference.saveUserAction(userId, article.getId(), article.getCategory(), "dislike");

        if (articlesContainer != null && articleBox != null) {
            articlesContainer.getChildren().remove(articleBox);
            System.out.println("Skipped and removed article: " + article.getTitle());
        }
    }
}
