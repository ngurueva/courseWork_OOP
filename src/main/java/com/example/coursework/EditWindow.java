package com.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.coursework.*;
import java.io.IOException;

public class EditWindow {
    People people;
    DBWorker dbWorker=new DBWorker();
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

    public EditWindow(People people){
        this.people = people;
    }

    public EditWindow(){
    }
    public void openEditWindow() throws IOException {
        textFieldSurname.setText(people.getSurname());
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
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
}