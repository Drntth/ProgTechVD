package com.libra.models;

public class Book {
    private static int lastAssignedId = 0;
    private int id;
    private String title;
    private String author;
    private double price;
    private int amount;

    public Book(String title, String author, double price, int amount) {
        this.id = ++lastAssignedId;
        setTitle(title);
        setAuthor(author);
        setPrice(price);
        setAmount(amount);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.price = builder.price;
        this.amount = builder.amount;
    }

    public static class Builder {
        private static int lastAssignedId = 0;
        private int id;
        private String title;
        private String author;
        private double price;
        private int amount;

        public Builder() {
            this.id = ++lastAssignedId;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}