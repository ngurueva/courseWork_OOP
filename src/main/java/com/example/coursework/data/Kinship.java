package com.example.coursework.data;

import java.util.ArrayList;

public class Kinship {
    private int id;
    private int firstPerson;
    private int secondPerson;
    private String relatives;
    public ArrayList<People> peopleArrayList = new ArrayList<People>();
    public Kinship(int id, int firstPerson, int secondPerson, String relatives) {
        this.id = id;
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.relatives = relatives;
    }

    public int getId() {
        return id;
    }

    public int getFirstPerson() {
        return firstPerson;
    }

    public int getSecondPerson() {
        return secondPerson;
    }

    public String getRelatives() {
        return relatives;
    }

    public void setId(int id) {
        this.id = id;
    }
}
