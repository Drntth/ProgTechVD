package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import com.libra.MainApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @FXML
    private void openUserProfile() {
        System.out.println("Felhasználói profil megnyitása...");
    }


    public void setApp(MainApplication mainApplication) {
        mainApplication.loadMainPageScene();
    }

    public void initialize() {
        loadUserData();
    }

    private String currentUser;
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    private void loadUserData() {
        String currentUser = mainApplication.getCurrentUser();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String query = "SELECT name, username, email FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, currentUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String username = rs.getString("username");
                String email = rs.getString("email");
                nameField.setText(name);
                usernameField.setText(username);
                emailField.setText(email);
            }
        } catch (SQLException e) {
            displayErrorDialog("Hiba az adatok betöltése közben: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges() {
        String newName = nameField.getText();
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();

        if (newName.isEmpty() || newUsername.isEmpty() || newEmail.isEmpty()) {
            displayErrorDialog("Kérjük töltse ki az összes mezőt!");
            return;
        }

        // TODO: További adatellenőrzések, validációk (pl. email formátum)

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String updateQuery = "UPDATE users SET name = ?, username = ?, email = ? WHERE username = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, newName);
            updateStmt.setString(2, newUsername);
            updateStmt.setString(3, newEmail);
            updateStmt.setString(4, mainApplication.getCurrentUser());
            updateStmt.executeUpdate();

            displayInfoDialog("Profil frissítve!");

        } catch (SQLException e) {
            displayErrorDialog("Hiba a profil frissítése közben: " + e.getMessage());
        }
    }

    @FXML
    private void cancelChanges() {
        loadUserData();
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayInfoDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Információ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
