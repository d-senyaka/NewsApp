package controllers;

import org.example.API.ArticleFetcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMainController {

    @FXML
    private Button manageUsers;
    @FXML
    private Button manageArticles;
    @FXML
    private Button backButton;

    private final ArticleFetcher articleFetcher = new ArticleFetcher();

    public void handleFetchArticles() {
        articleFetcher.fetchAndProcessArticlesAsync();
    }


    @FXML
    public void initialize() {
        // Set up button actions
        manageUsers.setOnAction(event -> openNewWindow("/org/example/s/ManageUsers.fxml", "Manage Users"));

        manageArticles.setOnAction(event -> triggerNewsAPI());

        backButton.setOnAction(event -> openNewWindow("/org/example/s/AdminLog.fxml", "Admin Login"));
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new stage for the FXML
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) manageUsers.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "Unable to load " + title);
        }
    }

    private void triggerNewsAPI() {
        try {
            articleFetcher.fetchAndProcessArticles();
            showConfirmationAlert("Success", "API Turned on. New Articles added.");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to turn on the API. Please try again.");
        }
        handleFetchArticles();

    }

    private void showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
