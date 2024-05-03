package com.libra.controllers;

import com.libra.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

public class AddBookController {
    private MainApplication mainApplication;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField amountField;
    @FXML
    private Button addButton;
    @FXML
    private Button homeButton;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> addBook());
    }

    @FXML
    public void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        double price = Double.parseDouble(priceField.getText());
        int amount = Integer.parseInt(amountField.getText());
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, title);
            selectStmt.setString(2, author);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.err.println("Ez a könyv már szerepel az adatbázisban.");
                return;
            }

            String query = "INSERT INTO book (author, title, price, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, author);
            stmt.setString(2, title);
            stmt.setDouble(3, price);
            stmt.setInt(4, amount);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Könyv hozzáadva: " + title);
            } else {
                System.err.println("Nem sikerült hozzáadni a könyvet.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv hozzáadása közben: " + e.getMessage());
        }

        homeButton.setOnAction(event -> {
            mainApplication.loadMainPageScene();
        });
    }

    public void addBook(String title, String author, double price, int amount) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, title);
            selectStmt.setString(2, author);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.err.println("Ez a könyv már szerepel az adatbázisban.");
                return;
            }

            String query = "INSERT INTO book (author, title, price, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, author);
            stmt.setString(2, title);
            stmt.setDouble(3, price);
            stmt.setInt(4, amount);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Könyv hozzáadva: " + title);
            } else {
                System.err.println("Nem sikerült hozzáadni a könyvet.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv hozzáadása közben: " + e.getMessage());
        }
    }
}

