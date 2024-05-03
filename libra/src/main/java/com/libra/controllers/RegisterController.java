package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.libra.MainApplication;

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
            mainApplication.loadLoginScene();
        });
        loginLink.setOnAction(event -> {
            mainApplication.loadLoginScene();
        });
    }
}
