package com.libra;

import java.sql.*;

public class DatabaseConnection {
    private static final String DB_PATH = "db.sqlite3";
    public static Connection connect() {
        Connection conn = null;
        try {
            String dbPrefix = "jdbc:sqlite:";
            conn = DriverManager.getConnection(dbPrefix + DB_PATH);
//            System.out.println("Adatbázis kapcsolat létehozva.");
        } catch (SQLException exception) {
            System.err.println("Hiba az adatbázis kapcsolatban: " + exception.getMessage());
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