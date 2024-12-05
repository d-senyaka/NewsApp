package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import user_management.AdminManageUser;
import user_management.UserDataForAdmin;

public class UserPopupController {

    @FXML
    private Label userIdLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button deleteButton;

    private UserDataForAdmin user;

    public void setUser(UserDataForAdmin user) {
        this.user = user;
        userIdLabel.setText(String.valueOf(user.getUserId()));
        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
    }

    @FXML
    public void initialize() {
        deleteButton.setOnAction(event -> handleDeleteUser());
    }

    private void handleDeleteUser() {
        if (AdminManageUser.deleteUser(user.getUserId())) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
