package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.observers.BookDeletedObserver;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteBookController {
    private MainApplication mainApplication;
    private List<BookDeletedObserver> observers = new ArrayList<>();
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button homeButton;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void initialize() {
        deleteButton.setOnAction(event -> deleteBook());
    }

    private void deleteBook() {
        String title = titleField.getText();
        String author = authorField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE author = ? AND title = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, author);
                selectStmt.setString(2, title);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        System.err.println("A könyv nem található az adatbázisban.");
                        return;
                    }
                }
            }

            String deleteQuery = "DELETE FROM book WHERE author = ? AND title = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, author);
                deleteStmt.setString(2, title);
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    notifyObservers(title);
                    // System.out.println("A könyv sikeresen törölve lett: " + title);
                } else {
                    System.err.println("Nem sikerült törölni a könyvet.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv törlése közben: " + e.getMessage());
        }

        homeButton.setOnAction(event -> {
            mainApplication.loadMainPageScene();
        });

        mainApplication.loadMainPageScene();
    }

    public void deleteBook(String title, String author) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE author = ? AND title = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, author);
                selectStmt.setString(2, title);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        System.err.println("A könyv nem található az adatbázisban.");
                        return;
                    }
                }
            }

            String deleteQuery = "DELETE FROM book WHERE author = ? AND title = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, author);
                deleteStmt.setString(2, title);
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                     System.out.println("A könyv sikeresen törölve lett: " + title);
                } else {
                    System.err.println("Nem sikerült törölni a könyvet.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv törlése közben: " + e.getMessage());
        }
    }

    public void addObserver(BookDeletedObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String title) {
        for (BookDeletedObserver observer : observers) {
            observer.onBookDeleted(title);
        }
    }
}
