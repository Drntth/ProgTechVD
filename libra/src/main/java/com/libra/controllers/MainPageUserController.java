package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.observers.BookAddedObserver;
import com.libra.observers.BookDeletedObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainPageUserController {

    private MainApplication mainApplication;
    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
    @FXML
    private void listBooks() {
        mainApplication.loadListBookScene();
    }

}
