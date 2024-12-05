package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user_management.AdminManageUser;
import user_management.UserDataForAdmin;

import java.io.IOException;

public class ManageUsersController {

    @FXML
    private TableView<UserDataForAdmin> usersTable;

    @FXML
    private TableColumn<UserDataForAdmin, Integer> userIdColumn;

    @FXML
    private TableColumn<UserDataForAdmin, String> usernameColumn;

    @FXML
    private TableColumn<UserDataForAdmin, String> emailColumn;

    @FXML
    private Button backButton;

    private final ObservableList<UserDataForAdmin> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load users into the table
        loadUsers();

        // Set row double-click action
        usersTable.setRowFactory(tv -> {
            TableRow<UserDataForAdmin> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    UserDataForAdmin selectedUser = row.getItem();
                    showUserPopup(selectedUser);
                }
            });
            return row;
        });

        // Back button action
        backButton.setOnAction(event -> goBackToAdminMain());
    }

    private void loadUsers() {
        userList.setAll(AdminManageUser.fetchAllUsers());
        usersTable.setItems(userList);
    }

    private void showUserPopup(UserDataForAdmin user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/UserPopup.fxml"));
            Parent root = loader.load();

            // Pass user details to the popup controller
            UserPopupController controller = loader.getController();
            controller.setUser(user);

            // Show the popup in a new window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("User Details");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            // Refresh the table in case the user was deleted
            loadUsers();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open user details popup.");
            e.printStackTrace();
        }
    }

    private void goBackToAdminMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/s/AdminMain.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Admin Main");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load Admin Main.");
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
