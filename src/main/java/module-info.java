module com.example.coursework.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;

    exports com.example.coursework.db;
    opens com.example.coursework.db to javafx.fxml;
    exports com.example.coursework.data;
    opens com.example.coursework.data to javafx.fxml;
    exports com.example.coursework.view;
    opens com.example.coursework.view to javafx.fxml;
    exports com.example.coursework;
    opens com.example.coursework to javafx.fxml;
}