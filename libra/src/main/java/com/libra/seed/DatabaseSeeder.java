package com.libra.seed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {
    private static final String DB_URL = "jdbc:sqlite:db.sqlite3";
    private static final String SQL_FILE_PATH = "src/main/resources/com/libra/sql/seeder.sql";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement statement = conn.createStatement()) {
            BufferedReader reader = new BufferedReader(new FileReader(SQL_FILE_PATH));
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
                sqlBuilder.append("\n");
            }
            reader.close();
            String sqlCommands = sqlBuilder.toString();
            statement.executeUpdate(sqlCommands);
            System.out.println("Seeder script successfully executed!");

        } catch (SQLException | java.io.IOException e) {
            System.err.println("Seeder error: " + e.getMessage());
        }
    }
}
