package com.libra;

import com.libra.controllers.DeleteBookController;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteBookTest {

    @Test
    void testDeleteBook() {
        DeleteBookController deleteBookController = new DeleteBookController();

        String title = "Teszt könyv címe";
        String author = "Teszt szerző";

        deleteBookController.deleteBook(title, author);

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
                    assertEquals(0, count, "A könyv még mindig az adatbázisban van.");
                }
            }
        } catch (SQLException e) {
            fail("Hiba történt az adatbázishoz való eltávolítás közben: " + e.getMessage());
        }
    }
}
