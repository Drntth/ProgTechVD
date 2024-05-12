package com.libra.models;

public class Order {
    private int id;
    private int userId;
    private String address;
    private int bookId;
    private int amount;
    private double price;
    private int stateId;

    public Order(int userId, String address, int bookId, int amount, double price, int stateId) {
        this.userId = userId;
        this.address = address;
        this.bookId = bookId;
        this.amount = amount;
        this.price = price;
        this.stateId = stateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    private Order(Builder builder) {
        this.userId = builder.userId;
        this.address = builder.address;
        this.bookId = builder.bookId;
        this.amount = builder.amount;
        this.price = builder.price;
        this.stateId = builder.stateId;
    }

    public static class Builder {
        private int userId;
        private String address;
        private int bookId;
        private int amount;
        private double price;
        private int stateId;

        public Builder() {

        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setBookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setStateId(int stateId) {
            this.stateId = stateId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
