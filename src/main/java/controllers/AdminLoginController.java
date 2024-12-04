package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user_management.AdminUser;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private TextField adminNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button confirmLoginButton, backButton, homeButton;

    @FXML
    public void initialize() {
        confirmLoginButton.setOnAction(event -> handleLogin());
        backButton.setOnAction(event -> navigateToFXML("/org/example/s/login.fxml", "Home"));
        homeButton.setOnAction(event -> navigateToFXML("/org/example/s/home.fxml", "Home"));
    }

    @FXML
    private void handleLogin() {
        String adminName = adminNameField.getText();
        String password = passwordField.getText();

        // Use AdminUser for authentication
        if (AdminUser.authenticate(adminName, password)) {
            AdminUser adminUser = new AdminUser(adminName, password); // Create AdminUser instance
            System.out.println("Admin logged in: " + adminUser.getUsername());

            // Navigate to AdminMain.fxml
            navigateToFXML("/org/example/s/AdminMain.fxml", "Admin Main");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid admin credentials.");
        }
    }

    private void navigateToFXML(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) confirmLoginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load " + title + ".");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
