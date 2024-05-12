package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.models.Order;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.libra.models.CurrentUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MyOrdersController {
    private MainApplication mainApplication;

    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, String> addressColumn;

    @FXML
    private TableColumn<Order, Integer> amountColumn;

    @FXML
    private TableColumn<Order, Double> priceColumn;


    @FXML
    private void home() {
        mainApplication.loadMainPageUserScene();
    }

    @FXML
    private void initialize() {
        try {
            String currentUsername = getCurrentUsername();
            int userId = getUserIdByUsername(currentUsername);

            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            String query = "SELECT user_id, address, book_id, amount, price, state_id FROM orders WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Order> orders = FXCollections.observableArrayList();
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

            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            ordersTableView.setItems(orders);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentUsername() {
        List<CurrentUser> userList = CurrentUser.getCurrentUserList();
        if (!userList.isEmpty()) {
            return userList.get(0).getUsername();
        }
        return null;
    }

    private int getUserIdByUsername(String username) {
        int userId = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String query = "SELECT id FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
}
