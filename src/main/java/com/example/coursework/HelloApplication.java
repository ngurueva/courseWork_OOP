package com.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(fxmlLoader.load(), 320, 280);
        stage.setTitle("Генеалогическое древо");
        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}