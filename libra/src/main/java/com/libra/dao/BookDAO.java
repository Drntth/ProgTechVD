package com.libra.dao;

import com.libra.models.Book;
import com.libra.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String SELECT_ALL_BOOKS_QUERY = "SELECT title, author, price, amount FROM book";

    public List<Book> getAllBooks() throws DatabaseException {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                int amount = resultSet.getInt("amount");

                books.add(new Book(title, author, price, amount));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Hiba az adatbázisműveletek közben: " + e.getMessage(), e);
        }
        return books;
    }
}
