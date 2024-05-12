package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.dao.BookDAO;
import com.libra.exceptions.DatabaseException;
import com.libra.models.Book;
import com.libra.util.MessageHandler;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.libra.models.CurrentUser;

import java.util.List;

public class ListBooksController {
    private MainApplication mainApplication;
    private BookDAO bookDAO = new BookDAO();

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Double> priceColumn;
    @FXML
    private TableColumn<Book, Integer> amountColumn;

    @FXML
    private void home() {
        int id = 0;
        int roleId = accessFirstUserRole(id);
        if (roleId == 2) {
            mainApplication.loadMainPageUserScene();
        } else if (roleId == 1) {
            mainApplication.loadMainPageScene();
        } else {
            System.out.print("Nem sikerült a terved Valentin");
        }
    }

    @FXML
    private void initialize() {
        try {
            List<Book> books = bookDAO.getAllBooks();

            ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
            titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
            amountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject());

            bookTableView.setItems(observableBooks);
        } catch (DatabaseException e) {
            MessageHandler.showError("Hiba az adatok betöltése közben: " + e.getMessage());
        }
    }

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    private int accessFirstUserRole(int roleId) {
        List<CurrentUser> userList = CurrentUser.getCurrentUserList();

        if (!userList.isEmpty()) {
            CurrentUser firstUser = userList.get(0);
            return firstUser.getRoleId();
        } else {
            return 0;
        }
    }
}
