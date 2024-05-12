package com.libra.observers;

public interface OrderAddedObserver {
    void onOrderAdded(String title, String shippingAddress, int amount);
}
