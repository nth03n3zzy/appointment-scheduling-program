module com.example.c195project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.c195project to javafx.fxml;
    opens models to javafx.base;
    exports com.example.c195project;
    exports controllers;
    opens controllers to javafx.fxml;


}