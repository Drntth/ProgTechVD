module com.libra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.libra to javafx.fxml;
    exports com.libra;
    exports com.libra.controllers;
    opens com.libra.controllers to javafx.fxml;
    opens com.libra.models to javafx.base;
}