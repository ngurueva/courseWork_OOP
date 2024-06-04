package com.example.coursework;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditWindow {
    People people;
    DBWorker dbWorker=new DBWorker();
    public ImageView imgPerson;
    public TextField textFieldSurname = new TextField();
    public TextField textFieldName = new TextField();
    public TextField textFieldPatronymic = new TextField();
    public TextField textFieldNickname = new TextField();
    public DatePicker textFieldDateOfBirth = new DatePicker();
    public DatePicker textFieldDateOfDeath = new DatePicker();
    public ComboBox comboBoxMom = new ComboBox();
    public ComboBox comboBoxDad = new ComboBox<>();
    public ComboBox comboBoxSpouse = new ComboBox<>();
    public ListView listViewChildern = new ListView();
    public MenuButton menuButtonGender = new MenuButton();
    public TextArea textFieldInfo = new TextArea();
    public Button BtnSave = new Button();
    Stage stage = new Stage();
    public Image image;
    public People person;
    private String filePath;

public void openEditWindow(People people) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    // Получаем контроллер сразу после загрузки FXML
    EditWindow controller = fxmlLoader.getController();


    controller.imgPerson.setImage(new Image(people.getPhoto()));
    // Теперь можно устанавливать значения в поля контроллера
//    controller.imgPerson.setImage(people.getPhoto());
    controller.textFieldSurname.setText(people.getSurname());
    controller.textFieldName.setText(people.getName());
    controller.textFieldPatronymic.setText(people.getPatronymic());
    controller.textFieldNickname.setText(people.getNickname());
    controller.textFieldInfo.setText(people.getInfo());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    if (!people.getDataOfBirth().isEmpty()) {
        LocalDate dateOfBirth = LocalDate.parse(people.dataOfBirth, formatter);
        controller.textFieldDateOfBirth.setValue(dateOfBirth);
    }
    if (!people.getDateOfDeath().isEmpty()) {
        LocalDate dateOfDeath = LocalDate.parse(people.dateOfDeath, formatter);
        controller.textFieldDateOfDeath.setValue(dateOfDeath);
    }
    controller.menuButtonGender.setText(people.getGender());

    Stage stage = new Stage();
    stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
    stage.setTitle("Редактирование");
    stage.setMinWidth(320);
    stage.setMinHeight(650);
    stage.setMaxWidth(320);
    stage.setMaxHeight(650);
    stage.setResizable(false);
    stage.setFullScreen(false);
    stage.setScene(scene);

    stage.show();
}

    public void edit() throws SQLException {

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

        dbWorker.editPeople(person);


//        person.editPeople(person.getId(), person);

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
}