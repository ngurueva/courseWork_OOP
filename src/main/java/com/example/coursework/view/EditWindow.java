package com.example.coursework.view;

import com.example.coursework.data.Kinship;
import com.example.coursework.db.DBWorker;
import com.example.coursework.data.People;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.coursework.HelloController.*;

public class EditWindow implements Initializable {
    Stage stage = new Stage();
    public ImageView imgPerson;
    public TextField textFieldSurname = new TextField();
    public TextField textFieldName = new TextField();
    public TextField textFieldPatronymic = new TextField();
    public TextField textFieldNickname = new TextField();
    public DatePicker textFieldDateOfBirth = new DatePicker();
    public DatePicker textFieldDateOfDeath = new DatePicker();
    public TextArea textFieldInfo = new TextArea();
    public Button BtnSave = new Button();
    public People person;
    private String filePath;
    private static int idPerson;
    private String dateOfBirthText;
    private String dateOfDeathText;
    private boolean flag = true;
    private static String gender;
    private DBWorker dbWorker = new DBWorker();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void openEditWindow(People people) throws IOException {
        idPerson = people.getId();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditWindow controller = fxmlLoader.getController();
        controller.imgPerson.setImage(new Image("file:" + people.getPhoto()));
        controller.textFieldSurname.setText(people.getSurname());
        controller.textFieldName.setText(people.getName());
        controller.textFieldPatronymic.setText(people.getPatronymic());
        controller.textFieldNickname.setText(people.getNickname());
        controller.textFieldInfo.setText(people.getInfo());
        gender = people.getGender();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (people.getDataOfBirth() != null && !people.getDataOfBirth().isEmpty()) {
            LocalDate dateOfBirth = LocalDate.parse(people.getDataOfBirth(), formatter);
            controller.textFieldDateOfBirth.setValue(dateOfBirth);
        }
        if (people.getDateOfDeath() != null && !people.getDateOfDeath().isEmpty()) {
            LocalDate dateOfDeath = LocalDate.parse(people.getDateOfDeath(), formatter);
            controller.textFieldDateOfDeath.setValue(dateOfDeath);
        }

        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setTitle("Редактирование");
        stage.setMinWidth(320);
        stage.setMinHeight(650);
        stage.setMaxWidth(320);
        stage.setMaxHeight(650);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            windowOpen = false;
        });
        stage.show();

        BtnSave.setOnMouseClicked(event -> {
            stage.close();
        });

    }
    public void edit() throws SQLException {
        int id = idPerson;
        String surnameText = textFieldSurname.getText();
        String nameText = textFieldName.getText();
        String patronymicText = textFieldPatronymic.getText();
        String nicknameText = textFieldNickname.getText();

        if (textFieldDateOfBirth.getValue() != null) {
            dateOfBirthText = textFieldDateOfBirth.getValue().toString();
        }
        if (textFieldDateOfDeath.getValue() != null) {
            dateOfDeathText = textFieldDateOfDeath.getValue().toString();
        }
        String infoText = textFieldInfo.getText();

        person = new People(id, surnameText, nameText, patronymicText, nicknameText, dateOfBirthText, dateOfDeathText, gender, filePath, infoText);

        dbWorker.editPeople(person);
        observablePeopleList.setAll(FXCollections.observableArrayList(dbWorker.getAllPeople()));
    }

    public void changeImg() {
        if (flag){
            flag = false;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(imageFilter);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                filePath = file.getAbsolutePath();
                Image image = new Image("file:" + filePath);
                imgPerson.setImage(image);
            }
            flag = true;
        }
    }
}
