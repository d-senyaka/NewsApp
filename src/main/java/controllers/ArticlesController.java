package controllers;

import article_categorization.Article;
import article_categorization.ArticleManager;
import article_categorization.GetUncatArticles;
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

        ArticleManager manager = new ArticleManager();

        // Example: Use default strategy to fetch all articles
        manager.setFetchStrategy(new GetUncatArticles());
        loadArticles(manager);


    }

    private void loadArticles(ArticleManager manager) {
        List<Article> articles = manager.getArticlesFromDatabase();

        if (articles.isEmpty()) {
            System.out.println("No articles found.");
            Text noArticlesText = new Text("No articles available at the moment.");
            articlesContainer.getChildren().add(noArticlesText);
            return;
        }

        for (Article article : articles) {
            VBox articleBox = createArticleBox(article);
            articlesContainer.getChildren().add(articleBox);
        }
    }

    private VBox createArticleBox(Article article) {
        VBox articleBox = new VBox();
        articleBox.getStyleClass().add("article-box");

        try {
            Image image = ImageCache.getInstance().getImage(article.getImageUrl());
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

        Text articleDetails = new Text(
                "Title: " + article.getTitle() + "\n" +
                        "Description: " + article.getDescription() + "\n" +
                        "Source: " + article.getSource() + "\n" +
                        "Author: " + article.getAuthor() + "\n" +
                        "Published: " + article.getPublishedAt()
        );
        articleDetails.getStyleClass().add("article-details");
        articleBox.getChildren().add(articleDetails);

        return articleBox;
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
