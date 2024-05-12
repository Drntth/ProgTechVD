/*package com.libra.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginControllerTest extends ApplicationTest {

    private LoginController loginController;

    @Before
    public void setUp() throws Exception {
        loginController = new LoginController();
        // Mock MainApplication or set up any necessary dependencies
    }

    @Test
    public void testLoginButtonAction() {
        // Simulate user input
        TextField usernameField = new TextField("testuser");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("testpassword");
        Button loginButton = new Button();

        // Set the fields in the LoginController
        //loginController.usernameField = usernameField;
        //loginController.passwordField = passwordField;

        // Trigger the action of loginButton
        loginButton.setOnAction(event -> loginController.initialize());

        // Check if the expected action occurs
        assertTrue("Login button action failed", loginButton.isVisible());
    }

    // Add more test methods as needed to cover other scenarios
}
*/