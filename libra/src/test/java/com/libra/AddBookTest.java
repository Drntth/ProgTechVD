package com.libra;

import com.libra.controllers.AddBookController;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AddBookTest {

    @Test
    void testAddBook() {
        AddBookController addBookController = new AddBookController();

        String title = "Teszt könyv címe";
        String author = "Teszt szerző";
        double price = 19.99;
        int amount = 10;

        addBookController.addBook(title, author, price, amount);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String query = "SELECT COUNT(*) FROM book WHERE title = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            int count = stmt.executeQuery().getInt(1);
            assertTrue(count == 1 , "A könyv nincs az adatbázisban.");
        } catch (SQLException e) {
            fail("Hiba történt az adatbázishoz való hozzáadás közben: " + e.getMessage());
        }
    }
}
