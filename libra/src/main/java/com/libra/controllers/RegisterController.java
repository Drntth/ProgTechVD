package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import com.libra.MainApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

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
    private PasswordField passwordAgainField;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void initialize() {
        registerButton.setOnAction(event -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String passwordAgain = passwordAgainField.getText();

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
                displayErrorDialog("Kérjük töltse ki az összes mezőt!");
                return;
            }

            if (!isValidEmail(email)) {
                displayErrorDialog("Érvénytelen email cím.");
                return;
            }

            if (password.length() < 5) {
                displayErrorDialog("A jelszó túl rövid!");
                return;
            }

            if (!password.equals(passwordAgain)) {
                displayErrorDialog("A két jelszó nem egyezik!");
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
                String checkUsernameSQL = "SELECT COUNT(*) FROM users WHERE username = ?";
                PreparedStatement checkStatement = conn.prepareStatement(checkUsernameSQL);
                checkStatement.setString(1, username);
                if (checkStatement.executeQuery().getInt(1) > 0) {
                    displayErrorDialog("Ezzel a felhasználónévvel már regisztráltak.");
                    return;
                }

                String insertUserSQL = "INSERT INTO users (name, username, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(insertUserSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.setInt(5, 2);
                preparedStatement.executeUpdate();

                System.out.println("Felhasználó regisztrálva: " + username);
                mainApplication.loadLoginScene();
            } catch (SQLException e) {
                displayErrorDialog("Hiba a regisztráció közben: " + e.getMessage());
            }
        });

        loginLink.setOnAction(event -> {
            mainApplication.loadLoginScene();
        });
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        return email.matches(regex);
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
