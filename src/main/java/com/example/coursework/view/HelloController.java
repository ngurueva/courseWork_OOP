package com.example.coursework.view;

import com.example.coursework.db.DBWorker;
import com.example.coursework.data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Repository repository;
    public HelloController() {
        this.repository = new DBWorker();
    }
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button ImgAddBtn;
    @FXML
    private Button ImgEditBtn;
    @FXML
    private Button ImgDeleteBtn;
    @FXML
    private TableView<People> tableView;
    @FXML
    private TableColumn<People, Integer> colId;
    @FXML
    private TableColumn<People, String> colSurname;
    @FXML
    private TableColumn<People, String> colName;
    @FXML
    private TableColumn<People, String> colPatronymic;
    @FXML
    private TableColumn<People, String> colNickname;
    @FXML
    private TableColumn<People, String> colAge;
    @FXML
    private TableColumn<People, String> colGender;
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
        repository.initDB();
        try {
            peopleList = repository.getAllPeople();
            kinship = repository.getAllKinship();
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
        repository.deletePeople(selectedPerson);
        repository.deleteRelative(selectedPerson);
        observablePeopleList.remove(observablePeopleList.indexOf(selectedPerson));
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