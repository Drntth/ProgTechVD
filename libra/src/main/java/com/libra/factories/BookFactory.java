package com.libra.factories;

import com.libra.models.Book;

public interface BookFactory {
    Book createBook(String title, String author, double price, int amount);
}
