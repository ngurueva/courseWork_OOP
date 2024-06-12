package com.example.coursework;

import com.example.coursework.db.DBWorker;
import com.example.coursework.data.*;
import com.example.coursework.view.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
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
    public TableView<People> tableView;
    public TableColumn<People, Integer> colId;
    public TableColumn<People, String> colSurname;
    public TableColumn<People, String> colName;
    public TableColumn<People, String> colPatronymic;
    public TableColumn<People, String> colNickname;
    public TableColumn<People, String> colAge;
    public TableColumn<People, String> colGender;
    private DBWorker dbWorker = new DBWorker();
    public static int newId = 0;
    public static int idRelative = 0;
    public static boolean windowOpen = false;
    private ArrayList<People> peopleList;
    private ArrayList<Kinship> kinship;
    public static ObservableList<People> observablePeopleList;
    public static ObservableList<Kinship> kinshipObservableList;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("dataOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dbWorker.initDB();
        try {
            peopleList = dbWorker.getAllPeople();
            kinship = dbWorker.getAllKinship();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < peopleList.size(); i++) {
            if (newId < peopleList.get(i).getId()) {
                newId = peopleList.get(i).getId();
                newId++;
            }
        }
        for (int i = 0; i < kinship.size(); i++) {
            if (idRelative < kinship.get(i).getId()) {
                idRelative = kinship.get(i).getId();
                idRelative++;
            }
        }
        observablePeopleList = FXCollections.observableArrayList(peopleList);
        tableView.setItems(observablePeopleList);
        kinshipObservableList = FXCollections.observableArrayList(kinship);
    }
    public void addPerson() throws IOException {
        if (!windowOpen) {
            AddWindow addWindow = new AddWindow();
            addWindow.openAddWindow();
            windowOpen = true;
        }
    }

    public void editPerson() throws IOException, SQLException {
        if (!windowOpen) {
            windowOpen = true;
            People selectedPerson = tableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                EditWindow editWindow = new EditWindow();
                editWindow.openEditWindow(selectedPerson);

            }
        }
    }

    public void deletePerson() throws SQLException {
        People selectedPerson = tableView.getSelectionModel().getSelectedItem();
        dbWorker.deletePeople(selectedPerson);
        dbWorker.deleteRelative(selectedPerson);
        observablePeopleList.remove(observablePeopleList.indexOf(selectedPerson));
    }

    public void openTree() {
    }

    public void closeTree() {
    }

    public void saveTree() {
    }
    public void clickingOnTable(MouseEvent mouseEvent) throws IOException, SQLException {
        if (mouseEvent.getClickCount() == 2) {
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
}