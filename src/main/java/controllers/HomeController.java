package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button viewArticlesButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button logInButton;

    @FXML
    public void initialize() {
        viewArticlesButton.setOnAction(event -> handleViewArticles());
        createAccountButton.setOnAction(event -> handleCreateAccount());
        logInButton.setOnAction(event -> handleLogIn());
    }


    private void handleViewArticles() {
        try {
            // Load the articles.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/articles.fxml"));
            Parent root = loader.load();

            // Create a new stage for the articles GUI
            Stage articlesStage = new Stage();
            articlesStage.setTitle("Articles");
            articlesStage.setScene(new Scene(root));
            articlesStage.show();

            // Close the current home GUI stage
            Stage currentStage = (Stage) viewArticlesButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load articles.fxml: " + e.getMessage());
        }
    }


    private void handleCreateAccount() {
        try {
            // Load the register.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/register.fxml"));
            Parent root = loader.load();

            // Create a new stage for the registration GUI
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root));
            registerStage.show();

            // Close the current home GUI stage
            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load register.fxml: " + e.getMessage());
        }
    }


    private void handleLogIn() {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/login.fxml"));
            Parent root = loader.load();

            // Create a new stage for the login GUI
            Stage loginStage = new Stage();
            loginStage.setTitle("Log In");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            // Close the current home GUI stage
            Stage currentStage = (Stage) logInButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
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
