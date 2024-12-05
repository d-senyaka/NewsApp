package user_action_handlers;

import article_categorization.Article;
import classes.UserSession;

public class ArticleRead {

    public void handle(Article article) {
        int userId = UserSession.getInstance().getUserId();
        if (userId == 0) {
            System.out.println("No user logged in. Action not saved.");
            return;
        }

        UserPreference.saveUserAction(userId, article.getId(), article.getCategory(), "read");
        System.out.println("Read action saved for article: " + article.getTitle());
    }
}
