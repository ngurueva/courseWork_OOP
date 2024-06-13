package com.example.coursework.data;


public class People {
    protected int id;
    protected String surname;
    protected String name;
    protected String patronymic;
    protected String nickname;
    protected String dataOfBirth;
    protected String dateOfDeath;
    protected String gender;
    protected String photo;
    protected String info;

    public People(int id, String surname, String name, String patronymic, String nickname, String dataOfBirth, String dateOfDeath, String gender, String photo, String info) {
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

    public String getPhoto() {
        return photo;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }
}
