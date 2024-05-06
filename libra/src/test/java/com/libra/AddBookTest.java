package com.libra;

import com.libra.controllers.AddBookController;
import com.libra.factories.BookFactory;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddBookTest {

    @Test
    void testAddBook() {
        BookFactory bookFactory = new BookFactory();
        AddBookController addBookController = new AddBookController();
        addBookController.setBookFactory(bookFactory);

        String title = "Teszt könyv címe";
        String author = "Teszt szerző";
        double price = 19.99;
        int amount = 10;

        addBookController.addBook(title, author, price, amount);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String query = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    int count = 0;
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                    assertTrue(count == 1, "A könyv nincs az adatbázisban.");
                }
            }
        } catch (SQLException e) {
            fail("Hiba történt az adatbázishoz való hozzáadás közben: " + e.getMessage());
        }
    }
}