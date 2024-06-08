package com.example.coursework.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class Tree {
    private ArrayList<People>list = new ArrayList<>();
    public ArrayList<People> getTree() {
        return list;
    }
    protected Repository repository;



    public Tree() {
        this.repository = repository;
        ImageIcon iconImage = new ImageIcon("com/example/coursework/close.png");
        Image image = iconImage.getImage();
        this.list.add(new People(1,"Гуруева",
                "Наталья",
                "Олеговна",
                "",
                "30.04.2004",
                "",
                "жен",
                image,
                "это я"));
    }

    ObservableList<People> treeList = FXCollections.observableArrayList(list);


//    public int getCount() {
//        return this.list.size();
//    }
//
//    public void add(People person) {
//        this.list.add(person);
//        System.out.println("Я прошел лист");
//        this.repository.addPerson(person);
//    }
//
//    public People getPerson(int index) {
//        return (People)this.list.get(index);
//    }
//
//    public void remove(int ind) {
//        this.list.remove(ind);
//    }
}
