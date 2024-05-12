package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.observers.BookDeletedObserver;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteBookController {
    private static final Logger logger = Logger.getLogger(DeleteBookController.class.getName());
    private MainApplication mainApplication;
    private List<BookDeletedObserver> observers = new ArrayList<>();
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private Button deleteButton;
    @FXML
    private void home() {
        mainApplication.loadMainPageScene();
    }

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public DeleteBookController() {

    }

    @FXML
    private void initialize() {
        deleteButton.setOnAction(event -> deleteBook());
    }

    private void deleteBook() {
        String title = titleField.getText();
        String author = authorField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, title);
                selectStmt.setString(2, author);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        logger.warning("A könyv nem található az adatbázisban.");
                        return;
                    }
                }
            }

            String deleteQuery = "DELETE FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, title);
                deleteStmt.setString(2, author);
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    notifyObservers(title);
                } else {
                    logger.warning("Nem sikerült törölni a könyvet.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Hiba a könyv törlése közben: " + e.getMessage(), e);
        }

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
                        logger.warning("A könyv nem található az adatbázisban.");
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
                    logger.info("A könyv sikeresen törölve lett: " + title);
                } else {
                    System.err.println("Nem sikerült törölni a könyvet.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Hiba a könyv törlése közben: " + e.getMessage(), e);
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
