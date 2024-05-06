package com.libra.factories;
import com.libra.models.Book;

public class MockBookFactory implements BookFactory {

    @Override
    public Book createBook(String title, String author, double price, int amount) {
        return new Book(title, author, price, amount);
    }
}
