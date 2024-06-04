package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.coursework.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    public HelloController() {
    }
    ArrayList<People> peopleList = null;
    ObservableList<People> observablePeopleList;
    public Button addBtn;
    public Button editBtn;
    public Button deleteBtn;
    public Button openBtn;
    public Button saveBtn;
    public Button closeBtn;
    public Button ImgAddBtn;
    public Button ImgEditBtn;
    public Button ImgDeleteBtn;
    public Button ImgOpenBtn;
    public Button ImgSaveBtn;
    public Button ImgCloseBtn;

    //    ObservableList<People> tree = new ObservableList<People>(getTree);
    public TableView<People> tableView;
    public TableColumn<People, Integer> colId;
    public TableColumn<People, String> colSurname;
    public TableColumn<People, String> colName;
    public TableColumn<People, String> colPatronymic;
    public TableColumn<People, String> colNickname;
    public TableColumn<People, String> colAge;
    public TableColumn<People, String> colGender;
    DBWorker dbWorker=new DBWorker();



    public void addPerson() throws IOException {
        AddWindow addWindow = new AddWindow();
        addWindow.openAddWindow();
        refreshTable();
    }
    public void editPerson() throws IOException {
        People selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            EditWindow editWindow = new EditWindow();
            editWindow.openEditWindow(selectedPerson);
            refreshTable();
        }

    }

    public void deletePerson() throws SQLException {
        People selectedPerson = tableView.getSelectionModel().getSelectedItem();
        dbWorker.deletePeople(selectedPerson);
        refreshTable();
    }

    public void openTree() {
    }

    public void closeTree() {
    }
    public void saveTree() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Создать столбцы таблицы
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("dataOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Создать список людей
        dbWorker.initDB();
        // Заполнить список людьми

        try {
            peopleList = dbWorker.getAllPeople();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Преобразовать список людей в ObservableList
        observablePeopleList = FXCollections.observableArrayList(peopleList);

        // Установить ObservableList в таблицу
        tableView.setItems(observablePeopleList);
    }

    public void openCard(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) { // Проверяем, был ли двойной клик
            People selectedPerson = tableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                PersonCard personCard = new PersonCard();
                personCard.openCard(selectedPerson);
            }
        }
        deleteBtn.setDisable(false);
        editBtn.setDisable(false);
        ImgDeleteBtn.setDisable(false);
        ImgEditBtn.setDisable(false);
    }

    private void refreshTable() {
        try {
            peopleList = dbWorker.getAllPeople();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Преобразовать список людей в ObservableList
        observablePeopleList = FXCollections.observableArrayList(peopleList);
        // Установить ObservableList в таблицу
        tableView.setItems(observablePeopleList);
        // Обновить таблицу
        tableView.refresh();
    }




}