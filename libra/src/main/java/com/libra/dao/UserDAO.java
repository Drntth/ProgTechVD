package com.libra.dao;

import com.libra.models.CurrentUser;
import com.libra.exceptions.DatabaseException;
import com.libra.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:db.sqlite3";

    public CurrentUser authenticateUser(String username, String password) throws DatabaseException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                return new CurrentUser(id, name, username, email, roleId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Hiba az autentikáció közben: " + e.getMessage());
        }
    }

    public boolean isUsernameTaken(String username) throws DatabaseException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Hiba az adatbáziskapcsolatban: " + e.getMessage());
        }
    }

    public void registerUser(User user) throws DatabaseException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "INSERT INTO users (name, username, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, 2); // Például a role_id: 2 - felhasználó szerepkör
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Hiba a felhasználó regisztrálásakor: " + e.getMessage());
        }
    }
}