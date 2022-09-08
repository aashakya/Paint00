module com.example.paint00 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.paint00 to javafx.fxml;
    exports com.example.paint00;
}