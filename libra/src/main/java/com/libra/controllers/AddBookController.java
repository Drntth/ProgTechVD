package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.factories.BookFactory;
import com.libra.observers.BookAddedObserver;
import com.libra.models.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button homeButton;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public AddBookController(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
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

        Book book = bookFactory.createBook(title, author, price, amount);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, book.getTitle());
                selectStmt.setString(2, book.getAuthor());
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.err.println("Ez a könyv már szerepel az adatbázisban.");
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
                    notifyObservers(book.getTitle());
                    // System.out.println("A könyv hozzáadva: " + title);
                } else {
                    System.err.println("Nem sikerült hozzáadni a könyvet.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv hozzáadása közben: " + e.getMessage());
        }

        homeButton.setOnAction(event -> {
            mainApplication.loadMainPageScene();
        });

        mainApplication.loadMainPageScene();
    }

    public void addBook(String title, String author, double price, int amount) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, title);
                selectStmt.setString(2, author);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.err.println("Ez a könyv már szerepel az adatbázisban.");
                        return;
                    }
                }
            }

            String query = "INSERT INTO book (author, title, price, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, author);
                stmt.setString(2, title);
                stmt.setDouble(3, price);
                stmt.setInt(4, amount);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                     System.out.println("A könyv hozzáadva: " + title);
                } else {
                    System.err.println("Nem sikerült hozzáadni a könyvet.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv hozzáadása közben: " + e.getMessage());
        }
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