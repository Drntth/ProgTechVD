package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            if (!password.equals(passwordAgain)) {
                System.err.println("A megadott jelszavak nem egyeznek meg!");
                return;
            }
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
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
                System.err.println("Hiba a regisztráció közben: " + e.getMessage());
            }
        });

        loginLink.setOnAction(event -> {
            mainApplication.loadLoginScene();
        });
    }
}
