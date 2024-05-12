package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.libra.MainApplication;
import com.libra.models.CurrentUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileController {

    private MainApplication mainApplication;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        loadUserData();
    }


    @FXML
    private void listBook() {
        mainApplication.loadListBookScene();
    }
    @FXML
    private void listMyOrders(){
        mainApplication.loadMyOrdersPage();
    }
    @FXML
    private void profile() {
        mainApplication.loadProfileScene();
    }
    @FXML
    private void shopScene() {
        mainApplication.loadShopScene();
    }
    @FXML
    private void home(){
        mainApplication.loadMainPageUserScene();
    }
    @FXML
    private void logout() {
        CurrentUser.removeFirstUser();
        mainApplication.loadLoginScene();
    }

    public void loadUserData() {
        CurrentUser currentUser = getCurrentUser();
        if (currentUser != null) {
            nameField.setText(currentUser.getName());
            usernameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
        } else {
            displayErrorDialog("Nincs elérhető felhasználói adat.");
        }
    }

    private CurrentUser getCurrentUser() {
        if (!CurrentUser.getCurrentUserList().isEmpty()) {
            return CurrentUser.getCurrentUserList().get(0);
        }
        return null;
    }

    @FXML
    private void saveChanges() {
        String newName = nameField.getText();
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();

        if (newName.isEmpty() || newUsername.isEmpty() || newEmail.isEmpty()) {
            displayErrorDialog("Kérjük töltse ki az összes mezőt!");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String updateQuery = "UPDATE users SET name = ?, username = ?, email = ? WHERE username = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, newName);
            updateStmt.setString(2, newUsername);
            updateStmt.setString(3, newEmail);
            updateStmt.setString(4, getCurrentUser().getUsername());
            updateStmt.executeUpdate();

            displayInfoDialog("Profil frissítve!");

            CurrentUser currentUser = getCurrentUser();
            if (currentUser != null) {
                currentUser.setName(newName);
                currentUser.setUsername(newUsername);
                currentUser.setEmail(newEmail);
            }

        } catch (SQLException e) {
            displayErrorDialog("Hiba a profil frissítése közben: " + e.getMessage());
        }
    }

    @FXML
    private void cancelChanges() {
        loadUserData();
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Információ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
