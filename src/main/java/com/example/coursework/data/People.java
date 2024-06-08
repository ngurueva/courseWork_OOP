package com.example.coursework.data;

import java.awt.*;
import java.util.ArrayList;

public class People {
    private ArrayList<People> list = new ArrayList<>();
    protected Repository repository;
    protected int id;
    protected String surname;
    protected String name;
    protected String patronymic;
    protected String nickname;
    protected String dataOfBirth;
    protected String dateOfDeath;
    protected String gender;
    protected Image photo;
    protected String phot;
    protected String info;
    protected String fullName;
    protected int age;



    public People(int id, String surname, String name, String patronymic, String nickname, String dataOfBirth, String dateOfDeath, String gender,Image photo, String info) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.nickname = nickname;
        this.dataOfBirth = dataOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.photo = photo;
        this.info = info;
    }
    public People(String fullName, int age) {

        this.fullName = fullName;
        this.age = age;

    }

    public People(int id, String surname, String name, String patronymic, String nickname, String dataOfBirth, String dateOfDeath, String gender, String photo, String info) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.nickname = nickname;
        this.dataOfBirth = dataOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.gender = gender;
        this.phot = photo;
        this.info = info;
    }



    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGender() {
        return gender;
    }

    public String getDataOfBirth() {
        return dataOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

//    public Image getPhoto() {
//        return photo;
//    }
    public String getPhoto() {
        return phot;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDataOfBirth(String dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

//    public void setPhoto(Image photo) {
//        this.photo = photo;
//    }
    public void setPhoto(String photo) {
        this.phot = photo;
    }

    public void setInfo(String info) {
        this.info = info;
    }





    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
