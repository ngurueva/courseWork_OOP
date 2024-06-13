package com.example.coursework.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository {
    void initDB();
    ArrayList<People> getAllPeople() throws SQLException;
    ArrayList<Kinship> getAllKinship() throws SQLException;
    ResultSet getPeopleData() throws SQLException;
    void addPeople(People people) throws SQLException;
    void addRelative(int id, int people1, int people2, String kinship) throws SQLException;
    void deletePeople(People people) throws SQLException;
    void deleteRelative(People people) throws SQLException;


    void editPeople(People people) throws SQLException;
    ResultSet getRelative(People people) throws SQLException;

}
