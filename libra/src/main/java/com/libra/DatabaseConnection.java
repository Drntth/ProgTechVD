package com.libra;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_PATH = "/com/libra/db.sqlite3";

    public static Connection connect() {
        Connection conn = null;
        try {
            try (InputStream inputStream = DatabaseConnection.class.getResourceAsStream(DB_PATH)) {
                if (inputStream == null) {
                    System.err.println("Az adatbázisfájl nem található: " + DB_PATH);
                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            conn = DriverManager.getConnection("jdbc:sqlite:");
            System.out.println("Adatbáziskapcsolat létrehozva.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}