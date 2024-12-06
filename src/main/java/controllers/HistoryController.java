package controllers;

import article_categorization.Article;
import user_management.ArticleActionForHistory;
import user_management.UserArticleHistory;
import classes.UserSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HistoryController {

    @FXML
    private ComboBox<String> preferencesDropdown;

    @FXML
    private Label selectedPreferencesLabel;

    @FXML
    private VBox articlesContainer;

    @FXML
    private ScrollPane articlesScrollPane;

    @FXML
    private Label userLabel;

    @FXML
    private Button myAccount;

    @FXML
    private Button goToArticlesButton;

    @FXML
    private Button logOutButton;

    private final UserArticleHistory userArticleHistory = new UserArticleHistory();

    @FXML
    public void initialize() {



        System.out.println("Initializing HistoryController...");

        // Set user information
        UserSession userSession = UserSession.getInstance();
        String username = userSession.getUsername();
        int userId = userSession.getUserId();
        userLabel.setText(username != null ? "User: " + username : "User: Guest");

        // Enable dynamic resizing
        articlesScrollPane.setFitToWidth(true);

        // Populate dropdown
        preferencesDropdown.setItems(FXCollections.observableArrayList("All", "Read", "Like", "Dislike"));
        preferencesDropdown.setOnAction(event -> {
            String selectedPreference = preferencesDropdown.getValue();
            selectedPreferencesLabel.setText("Selected preferences: " + selectedPreference);
            loadArticles(userId, selectedPreference);
        });

        // Display all articles by default
        loadArticles(userId, "All");
    }

    /**
     * Loads and displays articles filtered by the selected action.
     *
     * @param userId The ID of the logged-in user.
     * @param action The selected action ("All", "Read", "Liked", "Disliked").
     */
    private void loadArticles(int userId, String action) {
        articlesContainer.getChildren().clear();
        List<ArticleActionForHistory> articlesWithActions = userArticleHistory.getArticlesWithActions(userId);

        for (ArticleActionForHistory articleActionForHistory : articlesWithActions) {
            if ("All".equals(action) || articleActionForHistory.getAction().equalsIgnoreCase(action)) {
                VBox articleBox = createArticleBox(articleActionForHistory.getArticle(), articleActionForHistory.getAction());
                articlesContainer.getChildren().add(articleBox);
            }
        }
    }

    /**
     * Creates a styled VBox for displaying an article.
     *
     * @param article The article to display.
     * @param action  The action associated with the article.
     * @return A VBox styled as an article box.
     */
    private VBox createArticleBox(Article article, String action) {
        VBox articleBox = new VBox();
        articleBox.getStyleClass().add("article-box");

        // Add image or fallback text
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

        // Add title
        Text titleText = new Text(article.getTitle());
        titleText.getStyleClass().add("article-title");

        // Add description
        Text descriptionText = new Text(article.getDescription());
        descriptionText.getStyleClass().add("article-description");

        // Add metadata
        Text metadataText = new Text("Source: " + article.getSource() + "\n" +
                "Author: " + article.getAuthor() + "\n" +
                "Published: " + article.getPublishedAt() + "\n" +
                "Action: " + action);
        metadataText.getStyleClass().add("article-metadata");

        articleBox.getChildren().addAll(titleText, descriptionText, metadataText);
        return articleBox;
    }


    @FXML
    private void openUserAccount() {
        try {
            // Load History.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/UserAccount.fxml"));
            Parent root = loader.load();

            // Create a new stage for the User Account GUI
            Stage userAccountStage = new Stage();
            userAccountStage.setTitle("Headlines Plus - My Account");
            userAccountStage.setScene(new Scene(root));
            userAccountStage.show();

            // Close the current stage
            Stage currentStage = (Stage) myAccount.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the History page.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void openArtCategory() {
        try {
            // Load ArtCategory.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/ArtCategory.fxml"));
            Parent root = loader.load();

            // Create a new stage for ArtCategory GUI
            Stage artCategoryStage = new Stage();
            artCategoryStage.setTitle("Headlines Plus - Categorized Articles");
            artCategoryStage.setScene(new Scene(root));
            artCategoryStage.show();

            // Close the current login GUI stage
            Stage currentStage = (Stage) goToArticlesButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the ArtCategory page.");
        }
    }

    @FXML
    private void logOut() {
        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Logout");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to log out?");

        // Add Yes and No buttons to the alert
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

        // Wait for the user to respond
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Handle the user's choice
        if (result.isPresent() && result.get() == yesButton) {
            try {
                // Load login.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/login.fxml"));
                Parent root = loader.load();

                // Create a new stage for the Login GUI
                Stage loginStage = new Stage();
                loginStage.setTitle("Log In");
                loginStage.setScene(new Scene(root));
                loginStage.show();

                // Close the current stage
                Stage currentStage = (Stage) logOutButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the Log In page.");
            }
        }
    }

}
