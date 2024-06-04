package com.example.coursework;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.coursework.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


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

        dbWorker.addPeople(person);
//        person.addPeople(person);

    }
    public void changeImg() {
        // Создание объекта FileChooser.
        FileChooser fileChooser = new FileChooser();
        // Установка фильтра файлов для отображения только изображений.
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        // Открытие диалогового окна выбора файла.
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Получение пути к выбранному файлу.
            filePath = file.getAbsolutePath();
            // Загрузка изображения по указанному пути.
            image = new Image("file:" + filePath);
            // Установка загруженного изображения в качестве нового изображения для картинки.
            imgPerson.setImage(image);
        }
    }
    public void cancel() {
        stage.close();
    }

}