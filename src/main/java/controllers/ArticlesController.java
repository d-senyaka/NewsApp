package controllers;

import classes.Article;
import classes.ArticleManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ArticlesController {

    @FXML
    private VBox articlesContainer;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    public void initialize() {
        loadArticles();
    }

    private void loadArticles() {
        ArticleManager manager = new ArticleManager();
        List<Article> articles = manager.getArticlesFromDatabase();

        if (articles == null) {
            // Handle the case where the articles list is null
            System.out.println("Database error occurred. Cannot load articles.");
            showAlert("Database Failure", "Unable to connect to the database. Please try again later.");
            return;
        }

        System.out.println("Fetched " + articles.size() + " articles from table_d.");
        for (Article article : articles) {
            VBox articleBox = new VBox();
            articleBox.getStyleClass().add("article-box");

            // Add image or fallback to "No Image" text
            try {
                Image image = new Image(article.getImageUrl(), true);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                articleBox.getChildren().add(imageView);
            } catch (Exception e) {
                Text noImageText = new Text("No Image");
                noImageText.getStyleClass().add("no-image-text");
                articleBox.getChildren().add(noImageText);
            }

            // Add article details
            Text articleText = new Text(
                    "Title: " + article.getTitle() + "\n" +
                            "Description: " + article.getDescription() + "\n" +
                            "Source: " + article.getSource() + "\n" +
                            "Author: " + article.getAuthor() + "\n" +
                            "Published: " + article.getPublishedAt() + "\n\n"
            );
            articleText.getStyleClass().add("article-text");
            articleBox.getChildren().add(articleText);

            articlesContainer.getChildren().add(articleBox);
        }
    }




    @FXML
    private void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/register.fxml"));
            Parent root = loader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root));
            registerStage.show();

            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load register.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Log In");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load login.fxml: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
