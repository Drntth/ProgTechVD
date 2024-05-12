package com.libra.decorators;

import com.libra.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookFilter {
    private List<Book> books;

    public BookFilter(List<Book> books) {
        this.books = books;
    }

    public List<Book> filterByTitle(String title) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    public List<Book> filterByAuthor(String author) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    public List<Book> filterByPrice(double minPrice, double maxPrice) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            double price = book.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    public List<Book> filterByAmount(int minAmount, int maxAmount) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            int amount = book.getAmount();
            if (amount >= minAmount && amount <= maxAmount) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }
}
