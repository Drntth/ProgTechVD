package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.factories.BookFactory;
import com.libra.observers.BookAddedObserver;
import com.libra.models.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddBookController {
    private MainApplication mainApplication;
    private List<BookAddedObserver> observers = new ArrayList<>();
    private BookFactory bookFactory;
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
    private void home() {
        mainApplication.loadMainPageScene();
    }

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public AddBookController() {

    }

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> addBook());
    }

    public void setBookFactory(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
    }

    @FXML
    public void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        double price = Double.parseDouble(priceField.getText());
        int amount = Integer.parseInt(amountField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, title);
                selectStmt.setString(2, author);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        conn.close();
                        showUpdateConfirmationDialog(title, author, price, amount);
                    } else {
                        insertNewBook(conn, title, author, price, amount);
                    }
                }
            }
        } catch (SQLException e) {
            displayErrorDialog("Hiba a könyv hozzáadása közben: " + e.getMessage());
        }

        mainApplication.loadMainPageScene();
    }

    private void insertNewBook(Connection conn, String title, String author, double price, int amount) throws SQLException {
        String query = "INSERT INTO book (author, title, price, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, author);
            stmt.setString(2, title);
            stmt.setDouble(3, price);
            stmt.setInt(4, amount);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                notifyObservers(title);
            } else {
                displayErrorDialog("Nem sikerült hozzáadni a könyvet.");
            }
        }
    }

    private void showUpdateConfirmationDialog(String title, String author, double price, int amount) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Könyv már szerepel az adatbázisban");
        alert.setHeaderText(null);
        alert.setContentText("Ez a könyv már szerepel az adatbázisban!\nSzeretné az árát és mennyiségét frissíteni?");

        ButtonType yesButton = new ButtonType("Igen");
        ButtonType noButton = new ButtonType("Nem");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
                    updateBookPriceAndAmount(conn, title, author, price, amount);
                } catch (SQLException e) {
                    displayErrorDialog("Hiba a könyv frissítése közben: " + e.getMessage());
                }
            } else if (buttonType == noButton) {
                clearInputFields();
            }
        });
    }

    private void updateBookPriceAndAmount(Connection conn, String title, String author, double price, int amount) throws SQLException {
        String updateQuery = "UPDATE book SET price = ?, amount = amount + ? WHERE title = ? AND author = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            updateStmt.setDouble(1, price);
            updateStmt.setInt(2, amount);
            updateStmt.setString(3, title);
            updateStmt.setString(4, author);
            updateStmt.executeUpdate();
        }
    }

    private void clearInputFields() {
        titleField.clear();
        authorField.clear();
        priceField.clear();
        amountField.clear();
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addObserver(BookAddedObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String title) {
        for (BookAddedObserver observer : observers) {
            observer.onBookAdded(title);
        }
    }

}