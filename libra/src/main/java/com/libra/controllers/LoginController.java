package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.libra.MainApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private MainApplication mainApplication;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registrationLink;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void initialize() {
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                displayErrorDialog("Kérjük töltse ki az összes mezőt!");
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Sikeres bejelentkezés: " + username);
                    mainApplication.loadMainPageScene();
                } else {
                    displayErrorDialog("Érvénytelen felhasználónév vagy jelszó");
                }
            } catch (SQLException e) {
                displayErrorDialog("Hiba a bejelentkezés közben: " + e.getMessage());
            }
        });

        registrationLink.setOnAction(event -> {
            mainApplication.loadRegisterScene();
        });
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
