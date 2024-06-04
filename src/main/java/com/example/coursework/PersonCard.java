package com.example.coursework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class PersonCard     {

    Stage stage = new Stage();
    DBWorker dbWorker;
    public ImageView imgPerson;
    public Label labelFIO = new Label();
    public Label labelNickname = new Label();
    public Label labelYears = new Label();
    public Label labelGender = new Label();
    public Label labelInfo = new Label();

    public People people;
    public void openCard(People people) throws IOException {
        this.people = people;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("person-card.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        PersonCard controller = fxmlLoader.getController();

        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setTitle("Карта");
        stage.setMinWidth(600);
        stage.setMinHeight(420);
        stage.setMaxWidth(600);
        stage.setMaxHeight(420);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);


        System.out.println(people.getPhoto());
        controller.imgPerson.setImage(new Image(people.getPhoto()));

        controller.labelFIO.setText(people.getSurname() + " " + people.getName() + " " + people.getPatronymic());
        controller.labelNickname.setText(people.getNickname());
        controller.labelGender.setText(people.getGender());
        if((!people.getDateOfDeath().isEmpty()) && (!people.getDataOfBirth().isEmpty())){
            controller.labelYears.setText("( " + people.getDataOfBirth() + " - " + people.getDateOfDeath() + " )\n");
        } else if (!people.getDateOfDeath().isEmpty()) {
            controller.labelYears.setText("... - " + people.getDateOfDeath() + "г");
        } else if (!people.getDataOfBirth().isEmpty()) {
            controller.labelYears.setText(people.getDataOfBirth());
        } else{
            controller.labelYears.setText("");
        }
        controller.labelInfo.setText(people.getInfo());

        stage.show();
    }

}