package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.models.CurrentUser;
import com.libra.observers.BookAddedObserver;
import com.libra.observers.BookDeletedObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainPageController implements BookAddedObserver, BookDeletedObserver {
    private MainApplication mainApplication;
    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void listBooks() {
        mainApplication.loadListBookScene();
    }

    @FXML
    private void logout() {
        CurrentUser.removeFirstUser();
        mainApplication.loadLoginScene();
    }

    @FXML
    private void addBook() {
        mainApplication.loadAddBookScene();
    }

    @FXML
    private void deleteBook() {
        mainApplication.loadDeleteBookScene();
    }

    @Override
    public void onBookAdded(String title) {
        showAlert("A könyv hozzáadva!", "A '" + title + "' című könyv sikeresen hozzáadva.");
    }
    @Override
    public void onBookDeleted(String title) {
        showAlert("A könyv törölve!", "A '" + title + "' című könyv sikeresen törölve lett.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    // TODO: main page controller
}
