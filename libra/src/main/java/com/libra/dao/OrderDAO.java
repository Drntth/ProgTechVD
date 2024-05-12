package com.libra.dao;

import com.libra.exceptions.DatabaseException;
import com.libra.models.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT user_id, address, book_id, amount, price, state_id FROM orders WHERE user_id = ?";

    public List<Order> getOrdersByUserId(int userId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID_QUERY)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int fetchedUserId = resultSet.getInt("user_id");
                String address = resultSet.getString("address");
                int bookId = resultSet.getInt("book_id");
                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");
                int stateId = resultSet.getInt("state_id");

                Order order = new Order(fetchedUserId, address, bookId, amount, price, stateId);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Hiba az adatbázisműveletek közben: " + e.getMessage(), e);
        }
        return orders;
    }
}
