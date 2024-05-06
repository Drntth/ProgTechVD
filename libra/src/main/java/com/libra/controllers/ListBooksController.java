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
    private TableColumn<Book, Void> orderColumn;
    @FXML
    private void home() {
        mainApplication.loadMainPageScene();
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

        orderColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));

        orderColumn.setCellFactory(param -> {
            TableCell<Book, Void> cell = new TableCell<>() {
                private final Button orderButton = new Button("Rendelés");

                {
                    orderButton.setOnAction(event -> {
                        Book selectedBook = getTableView().getItems().get(getIndex());
                        if (selectedBook != null) {
                            showOrderDialog(selectedBook);
                        } else {
                            System.err.println("Nem sikerült a kiválasztott könyvet megkapni.");
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(orderButton);
                    }
                }
            };
            return cell;
        });

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

        root.getChildren().addAll(titleLabel, authorLabel, priceLabel, quantityField, shippingAddressField, orderButton); // Add shippingAddressField to the layout

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
