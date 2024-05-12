package com.libra.controllers;

import com.libra.MainApplication;
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

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookController {
    private static final Logger logger = Logger.getLogger(AddBookController.class.getName());
    private MainApplication mainApplication;
    private List<BookAddedObserver> observers = new ArrayList<>();
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

    @FXML
    public void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        double price = Double.parseDouble(priceField.getText());
        int amount = Integer.parseInt(amountField.getText());

        Book book = new Book.Builder()
                .setTitle(title)
                .setAuthor(author)
                .setPrice(price)
                .setAmount(amount)
                .build();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, book.getTitle());
                selectStmt.setString(2, book.getAuthor());
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        conn.close();
                        showUpdateConfirmationDialog(title, author, price, amount);
                        logger.warning("Ez a könyv már szerepel az adatbázisban.");
                        return;
                    } else {
                        insertNewBook(conn, title, author, price, amount);
                    }
                }
            }
        } catch (SQLException e) {
            displayErrorDialog("Hiba a könyv hozzáadása közben: " + e.getMessage());
            logger.log(Level.SEVERE, "Hiba a könyv hozzáadása közben: " + e.getMessage(), e);
        }

        mainApplication.loadMainPageScene();
    }

    public void addBook(Book book) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, book.getTitle());
                selectStmt.setString(2, book.getAuthor());
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        logger.warning("Ez a könyv már szerepel az adatbázisban.");
                        return;
                    }
                }
            }

            String query = "INSERT INTO book (author, title, price, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, book.getAuthor());
                stmt.setString(2, book.getTitle());
                stmt.setDouble(3, book.getPrice());
                stmt.setInt(4, book.getAmount());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info(String.format("A könyv hozzáadva: %s", book.getTitle()));
                } else {
                    logger.severe("Nem sikerült hozzáadni a könyvet.");
                }
            }
        } catch (SQLException e) {
            logger.severe(String.format("Hiba a könyv hozzáadása közben: %s", e.getMessage()));
        }
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