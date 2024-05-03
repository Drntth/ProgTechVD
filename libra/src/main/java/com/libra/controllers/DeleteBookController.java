package com.libra.controllers;

import com.libra.MainApplication;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

public class DeleteBookController {
    private MainApplication mainApplication;
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
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, author);
            selectStmt.setString(2, title);
            ResultSet resultSet = selectStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                System.err.println("A könyv nem található az adatbázisban.");
                return;
            }

            String deleteQuery = "DELETE FROM book WHERE author = ? AND title = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setString(1, author);
            deleteStmt.setString(2, title);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A könyv sikeresen törölve lett: " + title);
            } else {
                System.err.println("Nem sikerült törölni a könyvet.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv törlése közben: " + e.getMessage());
        }

        homeButton.setOnAction(event -> {
            mainApplication.loadMainPageScene();
        });
    }

    public void deleteBook(String title, String author) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, title);
            selectStmt.setString(2, author);
            ResultSet resultSet = selectStmt.executeQuery();
            if (!(resultSet.next() && resultSet.getInt(1) > 0)) {
                System.err.println("A könyv nem található az adatbázisban.");
                return;
            }

            String deleteQuery = "DELETE FROM book WHERE title = ? AND author = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setString(1, title);
            deleteStmt.setString(2, author);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A könyv sikeresen törölve lett: " + title);
            } else {
                System.err.println("Nem sikerült törölni a könyvet.");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a könyv törlése közben: " + e.getMessage());
        }
    }

}
