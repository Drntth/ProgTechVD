package com.libra;

import com.libra.controllers.*;
import com.libra.factories.BookFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    private Stage stage;
    private String currentUser;
    MainPageController mainPageController = new MainPageController();
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Bejelentkezés");
        stage.setResizable(false);
        stage.centerOnScreen();
        Image icon = new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("img/logo.png")));
        stage.getIcons().add(icon);
        loadLoginScene();
        stage.show();
    }

    public void loadLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/login-view.fxml"));
            Parent loginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setApp(this);
            Scene loginScene = new Scene(loginRoot, 600, 400);
            loginScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/login-register.css")).toExternalForm());
            stage.setScene(loginScene);
            stage.setTitle("Bejelentkezés");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRegisterScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/register-view.fxml"));
            Parent registerRoot = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setApp(this);
            Scene registerScene = new Scene(registerRoot, 600, 400);
            registerScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/login-register.css")).toExternalForm());
            stage.setScene(registerScene);
            stage.setTitle("Regisztráció");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMainPageScene(){
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/main-page.fxml"));
            Parent mainRoot = loader.load();
            MainPageController mainController = loader.getController();
            mainController.setApp(this);
            Scene mainScene = new Scene(mainRoot, 1024, 768);
            mainScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/main.css")).toExternalForm());
            stage.setScene(mainScene);
            stage.setTitle("Főoldal");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadMainPageUserScene(){
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/main-page-user.fxml"));
            Parent mainRoot = loader.load();
            MainPageUserController mainController = loader.getController();
            mainController.setApp(this);
            Scene mainScene = new Scene(mainRoot, 1024, 768);
            mainScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/mainUser.css")).toExternalForm());
            stage.setScene(mainScene);
            stage.setTitle("Főoldal");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAddBookScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/addBook-view.fxml"));
            Parent addBookRoot = loader.load();
            AddBookController addBookController = loader.getController();
            addBookController.setApp(this);

            BookFactory bookFactory = new BookFactory();
            addBookController.setBookFactory(bookFactory);

            addBookController.addObserver(mainPageController);

            Scene addBookScene = new Scene(addBookRoot, 600, 400);
            addBookScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/addBook.css")).toExternalForm());
            stage.setScene(addBookScene);
            stage.setTitle("Könyv hozzáadása");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadDeleteBookScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/deleteBook-view.fxml"));
            Parent deleteBookRoot = loader.load();
            DeleteBookController deleteBookController = loader.getController();
            deleteBookController.setApp(this);

            deleteBookController.addObserver(mainPageController);

            Scene deleteBookScene = new Scene(deleteBookRoot, 600, 400);
            deleteBookScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/deleteBook.css")).toExternalForm());
            stage.setScene(deleteBookScene);
            stage.setTitle("Könyv eltávolítása");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadListBookScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("fxml/listBooks-view.fxml"));
            Parent listBookRoot = loader.load();
            ListBooksController listBookController = loader.getController();
            listBookController.setApp(this);
            Scene listBookScene = new Scene(listBookRoot, 1024, 768);
            listBookScene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource("css/listBooks.css")).toExternalForm());
            stage.setScene(listBookScene);
            stage.setTitle("Könyv megrendelése");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProfileScene() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/libra/fxml/profile-view.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
            controller.setApp(this);
            controller.setCurrentUser(currentUser);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Profil szerkesztése");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    public static void main(String[] args) {
        launch();
    }
}