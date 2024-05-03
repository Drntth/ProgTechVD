package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.libra.MainApplication;

import java.io.File;

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
            mainApplication.loadMainPageScene();
        });
        registrationLink.setOnAction(event -> {
            mainApplication.loadRegisterScene();
        });
    }
}