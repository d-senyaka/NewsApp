package controllers;

import article_categorization.Article;
import article_categorization.ArticleCategorizer;
import classes.UserSession;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import article_recommendations.RecommendationService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RecommendationsController {

    @FXML
    private ComboBox<String> categoryDropdown;

    @FXML
    private Label selectedCategoryLabel;

    @FXML
    private VBox articlesContainer;

    @FXML
    private ScrollPane articlesScrollPane;

    @FXML
    private Label userLabel;

    @FXML
    private Button myHistory;

    @FXML
    private Button myAccount;

    @FXML
    private Button goToArticlesButton;

    @FXML
    private Button logOutButton;

    private final RecommendationService recommendationService = new RecommendationService();
    private List<Article> recommendedArticles;

    @FXML
    public void initialize() {


        int userId = UserSession.getInstance().getUserId();
        String username = UserSession.getInstance().getUsername();
        userLabel.setText("User: " + username);

        // Fetch recommended articles for the current user
        recommendedArticles = recommendationService.getRecommendedArticles(userId);

        // Populate the category dropdown
        categoryDropdown.setItems(FXCollections.observableArrayList(
                "All", "Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"
        ));

        // Display all recommended articles by default
        displayArticlesByCategory("All");

        // Add a listener to the category dropdown
        categoryDropdown.setOnAction(event -> {
            String selectedCategory = categoryDropdown.getValue();
            selectedCategoryLabel.setText("Selected Category: " + selectedCategory);
            displayArticlesByCategory(selectedCategory);
        });
    }

    /**
     * Filters and displays articles by category.
     *
     * @param category The category to filter by.
     */
    private void displayArticlesByCategory(String category) {
        articlesContainer.getChildren().clear();

        List<Article> filteredArticles = recommendationService.filterArticlesByCategory(recommendedArticles, category);

        if (filteredArticles.isEmpty()) {
            Text noArticlesText = new Text("No recommended articles found for the selected category.");
            noArticlesText.getStyleClass().add("no-articles-text");
            articlesContainer.getChildren().add(noArticlesText);
        } else {
            for (Article article : filteredArticles) {
                VBox articleBox = createArticleBox(article);
                articlesContainer.getChildren().add(articleBox);
            }
        }
    }

    /**
     * Creates a creative VBox for a single article.
     *
     * @param article The article to display.
     * @return A styled VBox containing the article details.
     */
    private VBox createArticleBox(Article article) {
        VBox articleBox = new VBox();
        articleBox.getStyleClass().add("article-box");

        // Ensure the article box spans the full width
        articleBox.prefWidthProperty().bind(articlesContainer.widthProperty().subtract(20)); // Adjust for padding

        // Add image or fallback to "No Image" text
        try {
            Image image = ImageCache.getInstance().getImage(article.getImageUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);  // Set width as per requirement
            imageView.setFitHeight(100); // Set height as per requirement
            imageView.setPreserveRatio(true); // Maintain aspect ratio
            articleBox.getChildren().add(imageView);
        } catch (Exception e) {
            Text noImageText = new Text("No Image");
            noImageText.getStyleClass().add("no-image-text");
            articleBox.getChildren().add(noImageText);
        }

        // Title
        Text titleText = new Text(article.getTitle());
        titleText.getStyleClass().add("article-title");

        // Description
        Text descriptionText = new Text(article.getDescription());
        descriptionText.getStyleClass().add("article-description");

        // Source, Author, and Published Date
        Text metadataText = new Text(
                "Source: " + article.getSource() + "\n" +
                        "Author: " + article.getAuthor() + "\n" +
                        "Published: " + article.getPublishedAt()
        );
        metadataText.getStyleClass().add("article-metadata");

        // Add all elements to the article box
        articleBox.getChildren().addAll(titleText, descriptionText, metadataText);

        return articleBox;
    }


    @FXML
    private void openHistoryGUI() {
        try {
            // Load History.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/UserHistory.fxml"));
            Parent root = loader.load();

            // Create a new stage for History GUI
            Stage historyStage = new Stage();
            historyStage.setTitle("Headlines Plus - History");
            historyStage.setScene(new Scene(root));
            historyStage.show();

            // Close the current stage (optional)
            Stage currentStage = (Stage) myHistory.getScene().getWindow();
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

    @FXML
    private void openArtCategory() {
        try {

            ArticleCategorizer articleCategorizer = new ArticleCategorizer();
            articleCategorizer.categorizeArticlesInParallel();

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


    public void handleHistoryButtonClick(ActionEvent actionEvent) {
        System.out.println("History button clicked. Fetching user history...");
    }
}
