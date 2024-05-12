package com.libra.models;

public class Book {
    private String title;
    private String author;
    private double price;
    private int amount;

    public Book(String title, String author, double price, int amount) {
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
}