package com.libra.factories;

import com.libra.models.Book;

public class BookFactory {
    public Book createBook(String title, String author, double price, int amount) {
        Book book = new Book(title, author, price, amount);
        return book;
    }
}

