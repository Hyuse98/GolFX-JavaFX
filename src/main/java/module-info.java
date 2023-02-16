module com.example.golfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.golfx to javafx.fxml;
    exports com.example.golfx;
}