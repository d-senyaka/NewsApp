module org.example.newsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.j;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.slf4j;

    // Export the main packages for external use
    exports classes;
    exports controllers;

    // Open only the required packages for JavaFX reflection
    opens controllers to javafx.fxml;
    opens classes to javafx.fxml;
    exports article_categorization;
    opens article_categorization to javafx.fxml;
}
