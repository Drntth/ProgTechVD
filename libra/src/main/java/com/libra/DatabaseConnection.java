package com.libra;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_PATH = "db.sqlite3";

    public static Connection connect() {
        Connection conn = null;
        Statement statement = null;
        try {
            try (InputStream inputStream = DatabaseConnection.class.getResourceAsStream(DB_PATH)) {
                if (inputStream == null) {
                    System.err.println("Az adatbázisfájl nem található: " + DB_PATH);
                    return null;
                }
                else{
                    System.out.println("Sikeres volt a megtalálás " + DB_PATH);
                }
            } catch (IOException e) {
                throw new RuntimeException("Hiba az adatbázisfájl betöltése közben", e);
            }
            conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            statement = conn.createStatement();
            System.out.println("Adatbáziskapcsolat létrehozva.");
        } catch (SQLException e) {
            throw new RuntimeException("Hiba az adatbáziskapcsolat létrehozása közben", e);
        }
        return conn;
    }

    public static boolean close(Connection conn, Statement statement) {
        boolean success = true;
        try {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
            conn = null;
            statement = null;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}