module com.libra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.libra to javafx.fxml;
    exports com.libra;
}