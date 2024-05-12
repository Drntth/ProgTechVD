package com.libra.controllers;

import com.libra.MainApplication;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.libra.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.libra.models.CurrentUser;
import java.util.List;

import java.sql.*;

import javafx.util.Callback;

public class ListBooksController {
    private MainApplication mainApplication;
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
        if(roleId == 2){
            mainApplication.loadMainPageUserScene();
        } else if (roleId == 1) {
            mainApplication.loadMainPageScene();
        }
        else {
            System.out.print("Nem sikerült a terved Valentin");
        }

    }

    @FXML
    private void initialize() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite3");
            String query = "SELECT title, author, price, amount FROM book";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Book> books = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                int amount = resultSet.getInt("amount");

                books.add(new Book(title, author, price, amount));
            }

            titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
            amountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject());

            bookTableView.setItems(books);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int accessFirstUserRole(int roleId) {
        List<CurrentUser> userList = CurrentUser.getCurrentUserList();

        if (!userList.isEmpty()) {
            CurrentUser firstUser = userList.get(0);

            int firstUserRole = firstUser.getRoleId();

            return firstUserRole;
        } else {
            return 0;
        }
    }

    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    private void showOrderDialog(Book book) {
        Stage stage = new Stage();
        stage.setTitle("Rendelés: " + book.getTitle());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label titleLabel = new Label("Cím: " + book.getTitle());
        Label authorLabel = new Label("Szerző: " + book.getAuthor());
        Label priceLabel = new Label("Ár: " + book.getPrice());

        TextField quantityField = new TextField();
        quantityField.setPromptText("Mennyiség");

        TextField shippingAddressField = new TextField();
        shippingAddressField.setPromptText("Szállítási cím");

        Button orderButton = new Button("Rendelés");
        orderButton.setOnAction(event -> {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    System.out.println("Megrendelve: " + quantity + " db " + book.getTitle());
                    System.out.println("Szállítási cím: " + shippingAddressField.getText());
                    stage.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Hiba");
                    alert.setHeaderText(null);
                    alert.setContentText("Hibás mennyiség!");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Hiba");
                alert.setHeaderText(null);
                alert.setContentText("Érvénytelen mennyiség!");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(titleLabel, authorLabel, priceLabel, quantityField, shippingAddressField, orderButton);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
