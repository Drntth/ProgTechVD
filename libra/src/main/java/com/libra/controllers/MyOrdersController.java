package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.models.Order;
import com.libra.exceptions.DatabaseException;
import com.libra.dao.OrderDAO;
import com.libra.models.CurrentUser;
import com.libra.util.MessageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class MyOrdersController {
    private MainApplication mainApplication;
    private OrderDAO orderDAO;

    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, String> addressColumn;

    @FXML
    private TableColumn<Order, Integer> amountColumn;

    @FXML
    private TableColumn<Order, Double> priceColumn;

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
        this.orderDAO = new OrderDAO();
        loadOrdersForCurrentUser();
    }

    @FXML
    private void listBook() {
        mainApplication.loadListBookScene();
    }
    @FXML
    private void listMyOrders(){
        mainApplication.loadMyOrdersPage();
    }
    @FXML
    private void profile() {
        mainApplication.loadProfileScene();
    }
    @FXML
    private void shopScene() {
        mainApplication.loadShopScene();
    }
    @FXML
    private void home(){
        mainApplication.loadMainPageUserScene();
    }
    @FXML
    private void logout() {
        CurrentUser.removeFirstUser();
        mainApplication.loadLoginScene();
    }

    private void loadOrdersForCurrentUser() {
        try {
            CurrentUser currentUser = getCurrentUser();
            if (currentUser != null) {
                int userId = currentUser.getId();
                List<Order> orders = orderDAO.getOrdersByUserId(userId);
                displayOrders(orders);
            }
        } catch (DatabaseException e) {
            MessageHandler.showError("Hiba az adatok betöltésekor: " + e.getMessage());
        }
    }

    private CurrentUser getCurrentUser() {
        List<CurrentUser> currentUserList = CurrentUser.getCurrentUserList();
        if (!currentUserList.isEmpty()) {
            return currentUserList.get(0);
        }
        return null;
    }

    private void displayOrders(List<Order> orders) {
        ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
        ordersTableView.setItems(observableOrders);
    }
}
