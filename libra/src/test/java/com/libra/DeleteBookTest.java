package com.libra;

import com.libra.controllers.DeleteBookController;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteBookTest {

    @Test
    void testAddBook() {
        DeleteBookController deleteBookController = new DeleteBookController();

        String title = "Teszt könyv címe";
        String author = "Teszt szerző";

        deleteBookController.deleteBook(title, author);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String query = "SELECT COUNT(*) FROM book WHERE title = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            int count = stmt.executeQuery().getInt(1);
            assertTrue(count == 0, "A könyv még mindig az adatbázisban van.");
        } catch (SQLException e) {
            fail("Hiba történt az adatbázishoz való eltávolítás közben: " + e.getMessage());
        }
    }
}
