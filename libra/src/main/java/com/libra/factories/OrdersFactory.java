package com.libra.factories;

import com.libra.models.Order;

public class OrdersFactory {
    public Order createOrder(int userId, String address, int bookId, int amount, double price, int stateId) {
        Order order = new Order(userId, address, bookId, amount, price, stateId);
        return order;
    }
}