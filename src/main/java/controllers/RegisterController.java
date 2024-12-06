package controllers;

import user_management.User;
import user_management.UserDB;
import classes.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private Button resetButton;

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> handleRegister());
        backButton.setOnAction(event -> goBack());
        resetButton.setOnAction(event -> handleReset());
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        try {
            User user = new User(username, email, password, confirmPassword);

            // Save user to the database
            UserDB userDB = new UserDB();
            int userId = userDB.saveUser(user);

            UserSession.getInstance().setUser(userId, username, email);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome to Headlines Plus!");

            // Navigate to ArtCategory.fxml
            openArtCategory();

        } catch (IllegalArgumentException e) {
            // Display user-friendly error messages (e.g., duplicate entry issues)
            showAlert(Alert.AlertType.ERROR, "Registration Error", e.getMessage());
        } catch (SQLException e) {
            // Handle other database errors
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save user data.");
            e.printStackTrace();
        }
    }


    private void openArtCategory() {
        try {
            // Load ArtCategory.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/ArtCategory.fxml"));
            Parent root = loader.load();

            // Create a new stage for ArtCategory GUI
            Stage artCategoryStage = new Stage();
            artCategoryStage.setTitle("Headlines Plus - Categories");
            artCategoryStage.setScene(new Scene(root));
            artCategoryStage.show();

            // Close the current registration GUI stage
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the ArtCategory page.");
        }
    }



    private void goBack() {
        // Show an informational alert with OK and Cancel options
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Back to Home");
        alert.setContentText("Are you sure you want to go back to Home page?");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Wait for the user to choose OK or Cancel
        Optional<ButtonType> result = alert.showAndWait();

        // If OK is clicked, navigate to home.fxml; if Cancel is clicked, do nothing
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Load the home.fxml file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/s/home.fxml"));
                Parent homeRoot = fxmlLoader.load();

                // Create a new stage for the home GUI
                Stage homeStage = new Stage();
                homeStage.setTitle("Headlines Plus");
                homeStage.setScene(new Scene(homeRoot));
                homeStage.show();

                // Close the current registration GUI stage
                Stage currentStage = (Stage) backButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the home page.");
                e.printStackTrace();
            }
        }
    }


    private void handleReset() {
        // Clear all fields in the registration form
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
