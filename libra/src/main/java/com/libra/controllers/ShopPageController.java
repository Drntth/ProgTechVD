package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.models.Book;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.libra.models.CurrentUser;
import java.util.List;

import java.sql.*;

public class ShopPageController {
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
        mainApplication.loadMainPageUserScene();
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
                if (quantity > 0 && quantity <= book.getAmount()) {
                    addOrder(book, quantity, shippingAddressField.getText());
                    updateBookAmount(book, quantity);
                    stage.close();
                } else {
                    displayErrorDialog("Hibás mennyiség!");
                }
            } catch (NumberFormatException e) {
                displayErrorDialog("Érvénytelen mennyiség!");
            }
        });

        root.getChildren().addAll(titleLabel, authorLabel, priceLabel, quantityField, shippingAddressField, orderButton);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addOrder(Book book, int quantity, String shippingAddress) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String selectQuery = "SELECT id FROM book WHERE title = ? AND author = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, book.getTitle());
            selectStmt.setString(2, book.getAuthor());
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int bookId = resultSet.getInt("id");

                String query = "INSERT INTO orders (user_id, address, book_id, amount, price, state_id) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, getCurrentUserId());
                stmt.setString(2, shippingAddress);
                stmt.setInt(3, bookId);
                stmt.setInt(4, quantity);
                stmt.setDouble(5, book.getPrice() * quantity);
                stmt.setInt(6, 1);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sikeres rendelés");
                    alert.setHeaderText(null);
                    alert.setContentText("A rendelés sikeresen rögzítve!");
                    alert.showAndWait();
                } else {
                    displayErrorDialog("Nem sikerült a rendelés rögzítése.");
                }
            } else {
                displayErrorDialog("Nem található a könyv az adatbázisban.");
            }
        } catch (SQLException e) {
            displayErrorDialog("Hiba a rendelés rögzítése közben: " + e.getMessage());
        }

        mainApplication.loadMainPageUserScene();
    }

    private void updateBookAmount(Book book, int orderedQuantity) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite3")) {
            String updateQuery = "UPDATE book SET amount = amount - ? WHERE title = ? AND author = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, orderedQuantity);
            updateStmt.setString(2, book.getTitle());
            updateStmt.setString(3, book.getAuthor());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            displayErrorDialog("Hiba a könyv mennyiség frissítése közben: " + e.getMessage());
        }
    }

    private int getCurrentUserId() {
        List<CurrentUser> userList = CurrentUser.getCurrentUserList();
        if (!userList.isEmpty()) {
            return userList.get(0).getId();
        }
        return 0;
    }

    private void displayErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
