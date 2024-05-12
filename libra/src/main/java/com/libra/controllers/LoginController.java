package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.libra.MainApplication;
import com.libra.models.CurrentUser;
import com.libra.dao.UserDAO;
import com.libra.exceptions.DatabaseException;
import com.libra.util.MessageHandler;

public class LoginController {
    private MainApplication mainApplication;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registrationLink;

    private UserDAO userDAO;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        this.userDAO = new UserDAO();
    }

    public void initialize() {
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                MessageHandler.showError("Kérjük töltse ki az összes mezőt!");
                return;
            }

            try {
                CurrentUser currentUser = userDAO.authenticateUser(username, password);
                if (currentUser != null) {
                    if(currentUser.getId() == 1) {
                        mainApplication.loadMainPageScene();
                    } else {
                        mainApplication.loadMainPageUserScene();
                    }
                } else {
                    MessageHandler.showError("Érvénytelen felhasználónév vagy jelszó");
                }
            } catch (DatabaseException e) {
                MessageHandler.showError("Hiba a bejelentkezés közben: " + e.getMessage());
            }
        });

        registrationLink.setOnAction(event -> {
            mainApplication.loadRegisterScene();
        });
    }
}
