package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML private Text titleText;
    @FXML private Button createAccountButton;
    @FXML private Button viewArticlesButton;
    @FXML private Button logInButton;

    @FXML
    public void initialize() {
        createAccountButton.setOnAction(event -> handleCreateAccount());
        viewArticlesButton.setOnAction(event -> handleViewArticles());
        logInButton.setOnAction(event -> handleLogIn());
    }

    private void handleCreateAccount() {
        openRegisterPage();
    }

    private void openRegisterPage() {
        try {
            // Load the register.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/s/register.fxml"));
            Parent registerRoot = fxmlLoader.load();

            // Get the current stage
            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(new Scene(registerRoot));
        } catch (IOException e) {
            showAlert("Error loading registration page: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void handleViewArticles() {
        showAlert("View Articles button clicked.");
    }


    private void handleLogIn() {
        openLoginPage();
    }

    private void openLoginPage() {
        try {
            // Load the login.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/s/login.fxml"));
            Parent loginRoot = fxmlLoader.load();

            // Get the current stage
            Stage currentStage = (Stage) logInButton.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(new Scene(loginRoot));
        } catch (IOException e) {
            showAlert("Error loading login page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
