package com.libra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

    private static final String DB_PATH = "db.sqlite3";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             Statement statement = conn.createStatement()) {
            String createRolesTableSQL = "CREATE TABLE IF NOT EXISTS roles (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL" +
                    ");";

            String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "username TEXT NOT NULL UNIQUE," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "role_id INTEGER," +
                    "FOREIGN KEY (role_id) REFERENCES roles(id)" +
                    ");";

            String createBookTableSQL = "CREATE TABLE IF NOT EXISTS book (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "author TEXT NOT NULL," +
                    "title TEXT NOT NULL," +
                    "price REAL NOT NULL," +
                    "amount INTEGER NOT NULL" +
                    ");";

            String createStatesTableSQL = "CREATE TABLE IF NOT EXISTS states (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL" +
                    ");";

            String createOrdersTableSQL = "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "address TEXT NOT NULL," +
                    "book_id INTEGER NOT NULL," +
                    "amount INTEGER NOT NULL," +
                    "price REAL NOT NULL," +
                    "state_id INTEGER NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (book_id) REFERENCES book(id)," +
                    "FOREIGN KEY (state_id) REFERENCES states(id)" +
                    ");";

            statement.executeUpdate(createRolesTableSQL);
            statement.executeUpdate(createUsersTableSQL);
            statement.executeUpdate(createBookTableSQL);
            statement.executeUpdate(createStatesTableSQL);
            statement.executeUpdate(createOrdersTableSQL);

            // Adatok beszúrása a roles táblába
            String insertRolesSQL = "INSERT INTO roles (id, name) VALUES (1, 'admin'), (2, 'user');";
            statement.executeUpdate(insertRolesSQL);

            // Adatok beszúrása a users táblába
            String insertUsersSQL = "INSERT INTO users (id, name, username, email, password, role_id) " +
                    "VALUES (1, 'admin', 'admin', 'admin@admin.com', 'admin', 1);";
            statement.executeUpdate(insertUsersSQL);

            System.out.println("Adattáblák létrehozva és adatok beszúrva: roles, users, book, states, orders");

        } catch (SQLException e) {
            System.err.println("Hiba az adattáblák létrehozása vagy adatok beszúrása közben: " + e.getMessage());
        }
    }
}
