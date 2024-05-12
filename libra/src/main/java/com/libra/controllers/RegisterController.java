package com.libra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.libra.MainApplication;
import com.libra.dao.UserDAO;
import com.libra.exceptions.DatabaseException;
import com.libra.models.User;
import com.libra.util.MessageHandler;

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

    private UserDAO userDAO;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        this.userDAO = new UserDAO(); // Inicializáljuk a UserDAO-t
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

            try {
                if (userDAO.isUsernameTaken(username)) {
                    displayErrorDialog("Ezzel a felhasználónévvel már regisztráltak.");
                    return;
                }

                User newUser = new User(name, username, email, password);
                userDAO.registerUser(newUser);

                System.out.println("Felhasználó regisztrálva: " + username);
                mainApplication.loadLoginScene();
            } catch (DatabaseException e) {
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
        MessageHandler.showError(message);
    }
}
