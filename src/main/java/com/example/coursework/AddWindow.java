package com.example.coursework;

import com.example.coursework.data.Kinship;
import com.example.coursework.db.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.coursework.data.People;
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
import java.util.ResourceBundle;


import static com.example.coursework.HelloController.*;


public class AddWindow implements Initializable {
    public SplitPane addWindowRoot;
    DBWorker dbWorker = new DBWorker();
    public ImageView imgPerson;
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldNickname;
    public DatePicker textFieldDateOfBirth;
    public DatePicker textFieldDateOfDeath;
    public ComboBox comboBoxMom;
    public ComboBox comboBoxDad;
    public ComboBox comboBoxSpouse;
    public ListView listViewChildern;
    public TextArea textFieldInfo;
    public Button BtnAdd;
    public Image image;
    public People person;
    private String filePath;
    public Stage stage = new Stage();
    private int momId;
    private int dadId;
    private int spouseId;
    private String gender;
    boolean flag = true;
    public ComboBox<String> genderComboBox = new ComboBox<>();
    public AddWindow() {
    }

    public void openAddWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setTitle("Добавление");
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
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void add() throws SQLException {
        imgPerson.setImage(image);
        String surnameText = textFieldSurname.getText();
        String nameText = textFieldName.getText();
        String patronymicText = textFieldPatronymic.getText();
        String nicknameText = textFieldNickname.getText();
        String dateOfBirthText = "";
        String dateOfDeathText = "";
        if (textFieldDateOfBirth.getValue() != null) {
            dateOfBirthText = textFieldDateOfBirth.getValue().toString();
        }
        if (textFieldDateOfDeath.getValue() != null) {
            dateOfDeathText = textFieldDateOfDeath.getValue().toString();
        }
        gender = genderComboBox.getValue();
        String infoText = textFieldInfo.getText();
        newId++;
        person = new People(newId, surnameText, nameText, patronymicText, nicknameText, dateOfBirthText, dateOfDeathText, gender, filePath, infoText);
        dbWorker.addPeople(person);
        if (comboBoxMom.getValue() != null){
            dbWorker.addRelative(idRelative, newId, momId, "мама");
            Kinship kinship = new Kinship(idRelative, newId, momId, "мама");
            kinshipObservableList.add(kinship);
            idRelative++;
            dbWorker.addRelative(idRelative, momId, newId, "ребенок");
            Kinship kinship1 = new Kinship(idRelative, newId, momId, "ребенок");
            kinshipObservableList.add(kinship1);
            idRelative++;
        }
        if (comboBoxDad.getValue() != null){
            dbWorker.addRelative(idRelative, newId, dadId, "отец");
            Kinship kinship2 = new Kinship(idRelative, newId, momId, "отец");
            kinshipObservableList.add(kinship2);
            idRelative++;
            dbWorker.addRelative(idRelative, dadId, newId, "ребенок");
            Kinship kinship3 = new Kinship(idRelative, newId, momId, "ребенок");
            kinshipObservableList.add(kinship3);
            idRelative++;
        }
        if ((comboBoxSpouse.getValue() != null) && (gender.toString() == "жен")){
            dbWorker.addRelative(idRelative, newId, spouseId, "муж");
            Kinship kinship4 = new Kinship(idRelative, newId, momId, "муж");
            kinshipObservableList.add(kinship4);
            idRelative++;
            dbWorker.addRelative(idRelative, spouseId, newId, "жена");
            Kinship kinship5 = new Kinship(idRelative, newId, momId, "жена");
            kinshipObservableList.add(kinship5);
            idRelative++;
        }else {
            dbWorker.addRelative(idRelative, newId, spouseId, "жена");
            Kinship kinship5 = new Kinship(idRelative, newId, momId, "жена");
            kinshipObservableList.add(kinship5);
            idRelative++;
            dbWorker.addRelative(idRelative, spouseId, newId, "муж");
            Kinship kinship4 = new Kinship(idRelative, newId, momId, "муж");
            kinshipObservableList.add(kinship4);
            idRelative++;
        }

        observablePeopleList.add(person);
        windowOpen = false;
        stage.close();
    }
    public void changeImg() {
        if (flag == true){
            flag = false;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(imageFilter);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                filePath = file.getAbsolutePath();
                image = new Image("file:" + filePath);
                imgPerson.setImage(image);
            }
            flag = true;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Заполнить выпадающий список пола значениями
        genderComboBox.getItems().addAll("муж", "жен");

        // Установить значение по умолчанию для пола
        genderComboBox.setValue("муж");

        // Заполнить выпадающие списки данными из базы данных
        try {
            dbWorker = new DBWorker();

            ObservableList<String> moms = FXCollections.observableArrayList();
            ResultSet resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                moms.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxMom.setItems(moms);

            ObservableList<String> dads = FXCollections.observableArrayList();
            resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                dadId = id;
                dads.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxDad.setItems(dads);

            ObservableList<String> spouses = FXCollections.observableArrayList();
            resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                spouseId = id;
                spouses.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxSpouse.setItems(spouses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
