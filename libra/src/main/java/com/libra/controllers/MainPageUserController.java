package com.libra.controllers;

import com.libra.MainApplication;
import com.libra.observers.BookAddedObserver;
import com.libra.observers.BookDeletedObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import com.libra.models.CurrentUser;

public class MainPageUserController {

    private MainApplication mainApplication;
    public void setApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
    @FXML
    private void listBooks() {
        mainApplication.loadListBookScene();
    }

    @FXML
    private void profilChanging(){mainApplication.loadProfileScene();}

    @FXML
    private void shopBooks(){
        mainApplication.loadShopScene();
    }

    @FXML
    private void myOrders(){
        mainApplication.loadMyOrdersPage();
    }

    @FXML
    private void logout() {
        CurrentUser.removeFirstUser();
        mainApplication.loadLoginScene();
    }

}
