package com.example.coursework.view;

import com.example.coursework.data.People;
import com.example.coursework.db.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class PersonCard{
    Stage stage = new Stage();
    public ImageView imgPerson;
    public Label labelFIO = new Label();
    public Label labelNickname = new Label();
    public Label labelYears = new Label();
    public Label labelGender = new Label();
    public Label labelInfo = new Label();
    public Label familyInfo = new Label();


    public void openCard(People people) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("person-card.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        PersonCard controller = fxmlLoader.getController();

        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setMinWidth(600);
        stage.setMinHeight(420);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);


        controller.imgPerson.setImage(new Image("file:C:/Users/Наталья/IdeaProjects/courseWork/src/main/resources/com/example/coursework/icons/noImg.png"));
        if (!Objects.equals(people.getPhoto(), "null") && people.getPhoto() != null) {
            controller.imgPerson.setImage(new Image("file:" + people.getPhoto()));
        }

        controller.labelFIO.setText(people.getSurname() + " " + people.getName() + " " + people.getPatronymic());
        controller.labelNickname.setText(people.getNickname());
        controller.labelGender.setText(people.getGender());

        if (people.getDateOfDeath() != null && !people.getDateOfDeath().isEmpty() && people.getDataOfBirth() != null && !people.getDataOfBirth().isEmpty()){
            controller.labelYears.setText("( " + people.getDataOfBirth() + " - " + people.getDateOfDeath() + " )\n");
        } else if (people.getDateOfDeath() != null && !people.getDateOfDeath().isEmpty()) {
            controller.labelYears.setText("(... - " + people.getDateOfDeath() + ")");
        } else if ((people.getDataOfBirth() != null && !people.getDataOfBirth().isEmpty())){
            controller.labelYears.setText(people.getDataOfBirth());
        } else{
            controller.labelYears.setText("");
        }

        controller.labelInfo.setText(people.getInfo());

        DBWorker dbWorker = new DBWorker();
        ObservableList<String> family = FXCollections.observableArrayList();
        ResultSet resultSet = dbWorker.getRelative(people);
        while (resultSet.next()) {
            int id_relative = resultSet.getInt("id");
            String fio = resultSet.getString("fio");
            String kinship = resultSet.getString("kinship");
            family.add(kinship + ": id " + id_relative + " " + fio + "\n");
        }

        controller.familyInfo.setText("Семья:\n" + String.valueOf(family));

        stage.show();
    }

}