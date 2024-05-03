package com.libra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
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
            fail("Nem sikerült létrehozni az utasítást.");
        }
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull(conn, "Az adatbáziskapcsolat nem lehet null.");
    }

    @Test
    public void testDatabaseOperations() {
        try {
            statement.setQueryTimeout(30);

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");

            ResultSet rs = statement.executeQuery("select * from person");

            assertTrue(rs.next(),"Az eredményhalmaznak sorokkal kell rendelkeznie.");
            assertEquals("leo", rs.getString("name"));
            assertEquals(1, rs.getInt("id"));

            assertTrue(rs.next(), "Az eredményhalmaznak további sorokkal kell rendelkeznie.");
            assertEquals("yui", rs.getString("name"));
            assertEquals(2, rs.getInt("id"));

            assertFalse(rs.next(), "Az eredményhalmaznak nem szabad több sorral rendelkeznie.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Az adatbázisművelet sikertelen: " + e.getMessage());
        } finally {
            try {
                statement.executeUpdate("drop table if exists person");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AfterEach
    public void tearDown() {
        assertTrue(DatabaseConnection.close(conn, statement), "A kapcsolat sikeresen lezárva.");
    }
}
