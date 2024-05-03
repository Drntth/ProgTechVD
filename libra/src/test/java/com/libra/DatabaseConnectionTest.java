package com.libra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {
    private Connection conn;
    private Statement statement;

    @BeforeEach
    public void setUp() {
        conn = DatabaseConnection.connect();
        assertNotNull(conn, "Az adatbáziskapcsolat nem lehet null.");

        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        assertTrue(DatabaseConnection.close(conn, statement), "A kapcsolat sikeresen lezárva.");
    }

    @Test
    public void testConnect() {
        assertNotNull(conn, "Az adatbáziskapcsolat nem lehet null.");
        assertNotNull(statement, "A Statement objektum nem lehet null.");
    }
}
