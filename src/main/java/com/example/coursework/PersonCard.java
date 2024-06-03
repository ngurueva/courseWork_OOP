package com.example.coursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonCard {

    Stage stage = new Stage();

    @FXML
    public Label labelFIO;
    @FXML
    public Label labelNickname;
    @FXML
    public Label labelYears;
    @FXML
    public Label labelGender;
    @FXML
    public Label labelInfo;

    private People people; // Добавляем переменную для хранения People объекта

    // Конструктор (необязателен)
    public PersonCard(People people) throws IOException {
        this.people = people;
        setCard(people);
    }

    public void openCard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("person-card.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Устанавливаем текущий PersonCard как контроллер для person-card.fxml
        fxmlLoader.setController(this);

        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setTitle("Карта");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxWidth(600);
        stage.setMaxHeight(400);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }


    public void setCard(People people) {
        if (this.labelFIO != null) {
            this.labelFIO.setText(people.getSurname() + " " + people.getName() + " " + people.getPatronymic());
        }
    }
}
