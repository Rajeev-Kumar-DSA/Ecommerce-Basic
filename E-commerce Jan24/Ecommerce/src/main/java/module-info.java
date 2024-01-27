module org.example.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.ecommerce to javafx.fxml;
    exports org.example.ecommerce;
}