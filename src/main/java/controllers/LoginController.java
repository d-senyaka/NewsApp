package controllers;

import user_management.UserLogin;
import classes.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button backButton; // New button for navigating back
    @FXML private Button AdminButton;


    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        backButton.setOnAction(event -> goBackToHome()); // Set event for backButton
    }

    private void handleLogin() {
        String usernameOrEmail = usernameField.getText();
        String password = passwordField.getText();

        UserLogin userLogin = new UserLogin();
        String result = userLogin.authenticate(usernameOrEmail, password);

        if (result != null) {
            showAlert(Alert.AlertType.ERROR, "Login Error", result);
        } else {
            int userId = UserSession.getInstance().getUserId();
            String username = UserSession.getInstance().getUsername();

            System.out.println("User logged in: ID=" + userId + ", Username=" + username);
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back, " + username + "!");
            openArtCategory();
        }
    }



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
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the ArtCategory page.");
        }
    }


    private void goBackToHome() {
        try {
            // Load the home.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/home.fxml"));
            Parent root = loader.load();

            // Create a new stage for the home GUI
            Stage homeStage = new Stage();
            homeStage.setTitle("Headlines Plus");
            homeStage.setScene(new Scene(root));
            homeStage.show();

            // Close the current login GUI stage
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the home page.");
        }
    }



    private void showAlert(Alert.AlertType alertType, String title, String content) {
        // Display the alert (either error or success message)
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void openAdminLogin() {
        try {
            // Load adminLog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/adminLog.fxml"));
            Parent root = loader.load();

            // Create a new stage for Admin GUI
            Stage artCategoryStage = new Stage();
            artCategoryStage.setTitle("Headlines Plus - Admin");
            artCategoryStage.setScene(new Scene(root));
            artCategoryStage.show();

            // Close the current login GUI stage
            Stage currentStage = (Stage) AdminButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the Admin page.");
        }
    }
}
