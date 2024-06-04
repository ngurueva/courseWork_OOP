package com.example.coursework;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;


public class AddWindow{
    DBWorker dbWorker=new DBWorker();
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
    public MenuButton menuButtonGender;
    public TextArea textFieldInfo;
    public Button BtnAdd;
    public Image image;
    public People person;
    private String filePath;

    Stage stage = new Stage();
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
        String gender = menuButtonGender.getText();
        String mom = (String) comboBoxMom.getValue();
        String dad = (String) comboBoxDad.getValue();
        String spouse = (String) comboBoxSpouse.getValue();
        ObservableList<String> selectedValues = listViewChildern.getSelectionModel().getSelectedItems();
        String infoText = textFieldInfo.getText();

        person = new People(hashCode(), surnameText, nameText, patronymicText, nicknameText, dateOfBirthText, dateOfDeathText, gender, ("file:" + filePath), infoText);
        System.out.println("ADD - " + person.getId() + " - " + person.getSurname());
        dbWorker.addPeople(person);
        stage.close();
    }
    public void changeImg() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePath = file.getAbsolutePath();
            image = new Image("file:" + filePath);
            imgPerson.setImage(image);
        }
    }
    public void cancel() {
        stage.close();
    }

}