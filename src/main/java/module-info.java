module org.example.newsapp {
    requires javafx.controls;
    requires javafx.fxml;
    exports classes;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.newsapp to javafx.fxml;
    exports org.example.newsapp;
    // Exports the classes package to other modules
    exports controllers;       // Allows other modules to access public members in controllers
    opens controllers to javafx.fxml;
}