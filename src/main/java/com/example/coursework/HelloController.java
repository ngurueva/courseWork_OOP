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
import javafx.stage.Stage;
import com.example.coursework.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HelloController {

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
//    public TreeTableView<People> treeTableView;
//    public TableColumn<People, String> colSurname;
//    public TableColumn<People, String> colName;
//    public TableColumn<People, String> colPatronymic;
//    public TableColumn<People, String> colNickname;
//    public TableColumn<People, Integer> colAge;
//    public TableColumn<People, String> colGender;



    public void addPerson() throws IOException {
        AddWindow addWindow = new AddWindow();
        addWindow.openAddWindow();
    }
    public void editPerson() throws IOException {
        EditWindow editWindow = new EditWindow();
        editWindow.openEditWindow();
    }

    public void deletePerson() {
    }

    public void openTree() {
    }

    public void closeTree() {
    }
    public void saveTree() {
    }

//    // Создать TableView
//    TableView<String> tableView = new TableView<>();
//
//    // Установить ObservableList в качестве элементов TableView
//        tableView.setItems(treeList);
//
//    // Отобразить TableView
//        tableView.show();

}