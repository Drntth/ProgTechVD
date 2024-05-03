package com.libra;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {
    @Test
    public void testConnection() {
        Connection conn = DatabaseConnection.connect();
        assertNotNull(conn, "Az adatbáziskapcsolat nem lehet null.");

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
