package controllers;

import article_categorization.Article;
import classes.UserPreference;
import classes.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ArticleReadController {

    @FXML
    private Text titleText;

    @FXML
    private Text metadataText;

    @FXML
    private ImageView articleImage;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private Button likeButton;

    @FXML
    private Button dislikeButton;

    private Article article;




    public void setArticle(Article article) {
        this.article = article;

        // Set article details in the GUI
        titleText.setText(article.getTitle());
        metadataText.setText("Source: " + article.getSource() + "\nAuthor: " + article.getAuthor() + "\nPublished: " + article.getPublishedAt());

        // Optimize content handling
        String content = article.getContent();
        if (content != null && content.length() > 1000) { // Limit content length
            content = content.substring(0, 1000) + "... [Content truncated]";
        }
        contentTextArea.setText(content);

        // Set article image if available
        try {
            Image image = new Image(article.getImageUrl(), 500, 500, true, true); // Limit image size
            articleImage.setImage(image);
        } catch (Exception e) {
            articleImage.setImage(null); // Use a placeholder image
            System.err.println("Error loading article image: " + e.getMessage());
        }
    }

    /*public void openArticleWindow(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/ArticleWindow.fxml"));
            Parent root = loader.load();

            // Pass the article and user to the new controller
            ArticleReadController controller = loader.getController();
            controller.setArticle(article);
            controller.setUser(UserSession.getInstance().getUsername());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Article Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */

    public void setUser(String username) {
        System.out.println("Article being viewed by: " + username);
        // You can use this information to customize the GUI further
    }

    @FXML
    private void handleLikeButtonAction() {


        // Save "like" action to the database
        int userId = UserSession.getInstance().getUserId();
        if (userId == 0) {
            System.out.println("No user logged in. Action not saved.");
            return;
        }

        int articleId = article.getId();
        String category = article.getCategory();

        UserPreference.saveUserAction(userId, articleId, category, "like");
        System.out.println("Liked article: " + article.getTitle());
    }


    @FXML
    private void handleDislikeButtonAction() {
        // Save "dislike" action to the database
        int userId = UserSession.getInstance().getUserId();
        if (userId == 0) {
            System.out.println("No user logged in. Action not saved.");
            return;
        }

        int articleId = article.getId();
        String category = article.getCategory();

        UserPreference.saveUserAction(userId, articleId, category, "dislike");
        System.out.println("Disliked article: " + article.getTitle());
    }
}
