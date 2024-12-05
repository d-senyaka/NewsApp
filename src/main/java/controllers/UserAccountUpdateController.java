package controllers;

import article_categorization.ArticleCategorizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user_management.UserAccountUpdater;
import classes.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserAccountUpdateController {

    @FXML
    private TextField usernameField, emailField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;
    @FXML
    private Button updateButton, myHistory, goToArticlesButton;

    @FXML
    private Button logOutButton;

    private final UserAccountUpdater updater = new UserAccountUpdater();

    @FXML
    public void initialize() {
        updateButton.setOnAction(event -> handleUpdateDetails());

    }

    private void handleUpdateDetails() {
        int userId = UserSession.getInstance().getUserId();
        if (userId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "User session not found.");
            return;
        }

        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if all fields are empty
        if (newUsername.isBlank() && newEmail.isBlank() && newPassword.isBlank() && confirmPassword.isBlank()) {
            showAlert(Alert.AlertType.INFORMATION, "No Updates", "No updates made.");
            resetFields();
            return;
        }

        // Validate passwords if they are not blank
        if (!newPassword.isBlank() && !newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            resetFields();
            return;
        }

        try {
            // Update user details, passing null for fields that are blank
            updater.updateUserDetails(userId,
                    newUsername.isBlank() ? null : newUsername,
                    newEmail.isBlank() ? null : newEmail,
                    newPassword.isBlank() ? null : newPassword);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Your details have been updated.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update details. Please try again.");
            e.printStackTrace();
        } finally {
            // Reset all fields regardless of success or failure
            resetFields();
        }
    }

    /**
     * Resets all input fields in the form.
     */
    private void resetFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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
