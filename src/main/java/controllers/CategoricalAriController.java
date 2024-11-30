package controllers;

import article_categorization.Article;
import article_categorization.ArticleCategorizer;
import article_categorization.KeywordCategoryStrategy;
import classes.*;
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

public class CategoricalArtController {

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
    private Button recommendationButton;

    @FXML
    private Button myAccount;

    private final ArticleCategorizer articleCategorizer = new ArticleCategorizer();

    @FXML
    public void initialize() {


        // Create an instance of ArticleCategorizer
        ArticleCategorizer articleCategorizer = new ArticleCategorizer();

// Set the categorization strategy to KeywordCategoryStrategy
        articleCategorizer.setCategoryStrategy(new KeywordCategoryStrategy());



        // Set the logged-in user
        UserSession userSession = UserSession.getInstance();
        String loggedInUser = userSession.getUsername();
        if (loggedInUser != null) {
            userLabel.setText("User: " + loggedInUser);
        } else {
            userLabel.setText("User: Guest");
        }


        // Enable dynamic resizing for articles in the ScrollPane
        articlesScrollPane.setFitToWidth(true);

        // Populate the ComboBox with categories
        categoryDropdown.setItems(FXCollections.observableArrayList(
                "Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology"
        ));

        // Add listener to handle selection changes
        categoryDropdown.setOnAction(event -> {
            String selectedCategory = categoryDropdown.getValue();
            if (selectedCategory != null) {
                selectedCategoryLabel.setText("Selected Category: " + selectedCategory);
                displayArticlesForCategory(selectedCategory);
            } else {
                // If no category is selected, display all articles
                selectedCategoryLabel.setText("All Articles");
                displayAllArticles();
            }
        });

        // Categorize and store articles on initialization
        categorizeAndStoreArticles();

        // Display all articles by default
        displayAllArticles();


    }

    /**
     * Display all articles from table_c.
     */
    private void displayAllArticles() {
        // Clear the current articles
        articlesContainer.getChildren().clear();

        // Fetch all articles from table_c
        List<Article> articles = articleCategorizer.getAllArticles();

        if (articles.isEmpty()) {
            Text noArticlesText = new Text("No articles found in the database.");
            noArticlesText.getStyleClass().add("no-articles-text");
            articlesContainer.getChildren().add(noArticlesText);
        } else {
            for (Article article : articles) {
                articlesContainer.getChildren().add(createArticleBox(article));
            }
        }
    }


    /**
     * Categorize and store articles from table_d into table_c.
     */
    private void categorizeAndStoreArticles() {
        System.out.println("Categorizing and storing articles...");
        articleCategorizer.categorizeAndStoreArticles();
    }

    /**
     * Display articles for the selected category.
     *
     * @param category The selected category.
     */
    @FXML
    private void displayArticlesForCategory(String category) {
        articlesContainer.getChildren().clear();

        List<Article> articles = articleCategorizer.getArticlesByCategory(category);

        if (articles.isEmpty()) {
            Text noArticlesText = new Text("No articles found for this category.");
            articlesContainer.getChildren().add(noArticlesText);
        } else {
            for (Article article : articles) {
                articlesContainer.getChildren().add(createArticleBox(article));
            }
        }
    }


    /**
     * Create a styled VBox for displaying a single article, including its image.
     *
     * @param article The article to display.
     * @return A VBox styled as an article box.
     */
    private VBox createArticleBox(Article article) {
        VBox articleBox = new VBox();
        articleBox.getStyleClass().add("article-box");

        // Ensure the article box spans the full width
        articleBox.prefWidthProperty().bind(articlesContainer.widthProperty().subtract(20)); // Adjust for padding

        // Add image or fallback to "No Image" text
        try {
            Image image = new Image(article.getImageUrl(), true);
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

        // Buttons: Read More, Like, Skip
        javafx.scene.control.Button readMoreButton = new javafx.scene.control.Button("Read More");
        readMoreButton.getStyleClass().add("read-more-button");
        readMoreButton.setOnAction(event -> {
            // Handle "read more" action
            ArticleRead articleReadHandler = new ArticleRead();
            articleReadHandler.handle(article);

            // Load and display the ArticleWindow.fxml GUI
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/ArticleWindow.fxml"));
                Parent root = loader.load();

                // Pass the article to the new controller
                ArticleReadController controller = loader.getController();
                controller.setArticle(article);

                // Create and display the new stage
                Stage stage = new Stage();
                stage.setTitle(article.getTitle()); // Set the window title to the article's title
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        javafx.scene.control.Button likeButton = new javafx.scene.control.Button("Like");
        likeButton.getStyleClass().add("like-button");
        likeButton.setOnAction(event -> {
            ArticleLike articleLikeHandler = new ArticleLike();
            articleLikeHandler.handle(article);
        });

        javafx.scene.control.Button skipButton = new javafx.scene.control.Button("Dislike");
        skipButton.getStyleClass().add("skip-button");
        skipButton.setOnAction(event -> {
            ConcreteArticleSkip articleSkipHandler = new ConcreteArticleSkip();
            articleSkipHandler.handle(article, articleBox, articlesContainer);
        });



        // HBox for buttons
        javafx.scene.layout.HBox buttonBox = new javafx.scene.layout.HBox(10, readMoreButton, likeButton, skipButton);
        buttonBox.getStyleClass().add("button-box");

        // Add all elements to the article box
        articleBox.getChildren().addAll(titleText, descriptionText, metadataText, buttonBox);

        return articleBox;
    }

    @FXML
    private void handleRecommendationButtonClick() {
        try {
            int userId = UserSession.getInstance().getUserId();

            // Validate user actions
            UserActionValidator validator = new UserActionValidator();
            int missingActions = validator.getMissingActions(userId);

            if (missingActions > 0) {
                // Show warning alert for insufficient actions
                showAlert(Alert.AlertType.WARNING, "Insufficient preferences","You are missing " + missingActions + " preferences."+
                        "You need at least 10 preferences (read, like, dislike) to get recommendations.\n");
                return; // Stop execution if the user has insufficient actions
            }

            // Show confirmation dialog if actions are sufficient
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Navigate to Recommendations");
            confirmationAlert.setHeaderText("Do you want to view your recommendations?");
            confirmationAlert.setContentText("Are you sure you want to go to Recommendations?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Trigger dataset creation and process recommendations
                        UserDatasetCreator datasetCreator = new UserDatasetCreator();

                        List<UserDatasetCreator.ArticlePreference> articles = datasetCreator.fetchAllArticlesWithPreferences(userId);
                        datasetCreator.saveUserDataset(userId, articles);

                        // Initialize the recommendation engine with a strategy
                        RecommendationStrategy strategy = new WeightedContentBasedStrategy();
                        RecommendationEngine recommendationEngine = new RecommendationEngine(strategy);
                        recommendationEngine.processRecommendations();

                        // Load Recommendations.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/Recommendations.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Recommendations");
                        stage.setScene(new Scene(root));
                        stage.show();

                        // Close the current window
                        Stage currentStage = (Stage) recommendationButton.getScene().getWindow();
                        currentStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to process recommendations.");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to validate user actions.");
        }
    }


    @FXML
    private void openHistoryGUI() {
        try {
            // Load History.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/UserHistory.fxml"));
            Parent root = loader.load();

            // Create a new stage for the History GUI
            Stage historyStage = new Stage();
            historyStage.setTitle("Headlines Plus - History");
            historyStage.setScene(new Scene(root));
            historyStage.show();

            // Close the current stage
            Stage currentStage = (Stage) myAccount.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the History page.");
        }
    }



    // New Helper Method to Show Alerts
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


}
