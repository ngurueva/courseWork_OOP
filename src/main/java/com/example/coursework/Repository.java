package com.example.coursework;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository {
    ArrayList<People> getAllPeople() throws SQLException;
    void addPeople(People people) throws SQLException;
    void deletePeople(People people) throws SQLException;


}
