package com.example.coursework.db;

import java.sql.*;
import java.util.ArrayList;

import com.example.coursework.data.Kinship;
import com.example.coursework.data.People;
import com.example.coursework.data.Repository;

public class DBWorker implements Repository {
    ArrayList<People> list = new ArrayList<People>();
    ArrayList<Kinship> listKinship = new ArrayList<Kinship>();
    protected People repository;
    private static String jdbUrl = "jdbc:sqlite:C:\\SQLite\\sqlite-tools-win-x64-3450200\\tree.db";
    private static Connection connection;


    ResultSet resultSet=null;
    public static void initDB(){
        try {
            connection = DriverManager.getConnection(jdbUrl);
            createTable();
        }
        catch (SQLException e){
            System.out.println("Ошибка с подключением бд");
            e.printStackTrace();
        }
    }
    public ResultSet getPeopleData() throws SQLException {
        String sql = "SELECT * FROM people";
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public static void createTable(){
        try {
            Statement statement=connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'people'('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'name' text, 'patronymic' text, 'nickname' text, 'dataOfBirth' text, 'dateOfDeath' text, 'gender' text, 'photo' text, 'info' text);");
            System.out.println("Таблица people успешно созданa");


            statement.execute("CREATE TABLE if not exists 'relatives'('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'id_person' INTERGER, 'id_relative' INTERGER, 'kinship' text);");
            System.out.println("Таблица relatives успешно созданa");

            System.out.println("Data successfully inserted");
            statement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void editPeople(People people) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        System.out.println("БД подключена!");

        try (PreparedStatement statement = connection.prepareStatement(
                "update people set surname=?, name=?, patronymic=?, nickname=?, dataOfBirth=?, dateOfDeath=?, gender=?, photo=?, info=? where id=?")) {

            statement.setString(1, people.getSurname());
            statement.setString(2, people.getName());
            statement.setString(3, people.getPatronymic());
            statement.setString(4, people.getNickname());
            statement.setString(5, people.getDataOfBirth()); // Предполагается, что это строка, а не дата
            statement.setString(6, people.getDateOfDeath()); // Предполагается, что это строка, а не дата
            statement.setString(7, people.getGender());
            statement.setString(8, people.getPhoto());
            statement.setString(9, people.getInfo());
            statement.setInt(10, people.getId()); // Предполагается, что id - целое число

            int rowsUpdated = statement.executeUpdate();

            System.out.println(people.getId() + " - " + people.getSurname());
            if (rowsUpdated > 0) {
                System.out.println("Данные о человеке изменены.");
            } else {
                System.out.println("Не удалось обновить данные о человеке.");
            }
        } finally {
            if (connection != null) {
                connection.close();
                System.out.println("Соединения закрыты");
            }
        }
    }


    public ArrayList<People> getAllPeople() throws SQLException {
        Connection conn = DriverManager.getConnection(jdbUrl);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people;");

        while(resultSet.next()) {
            list.add(new People(resultSet.getInt("id"), resultSet.getString("surname"), resultSet.getString("name"), resultSet.getString("patronymic"), resultSet.getString("nickname"), resultSet.getString("dataOfBirth"), resultSet.getString("dateOfDeath"), resultSet.getString("gender"), resultSet.getString("photo"), resultSet.getString("info")));
        }

        return list;
    }

    public ArrayList<Kinship> getAllKinship() throws SQLException{
        Connection conn = DriverManager.getConnection(jdbUrl);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM relatives;");

        while(resultSet.next()) {
            listKinship.add(new Kinship(resultSet.getInt("id"), resultSet.getInt("id_person"), resultSet.getInt("id_relative"), resultSet.getString("kinship")));
        }

        return listKinship;
    }

    @Override
    public void addPeople(People people) throws SQLException
    {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into people values (" + people.getId() + ", '" + people.getSurname() + "', '" + people.getName() + "', '" + people.getPatronymic() + "', '" + people.getNickname() + "', '" + people.getDataOfBirth() + "', '" + people.getDateOfDeath() + "', '" + people.getGender() + "', '" + people.getPhoto() + "', '" + people.getInfo() + "');");
        System.out.println("people добавлен в БД");

        System.out.println("Соединения закрыты");
        connection.close();
    }

    @Override
    public void addRelative(int id, int people1, int people2, String kinship) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbUrl);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO relatives (id, id_person, id_relative, kinship) VALUES (?, ?, ?, ?)")) {

            statement.setInt(1, id);
            statement.setInt(2, people1);
            statement.setInt(3, people2);
            statement.setString(4, kinship);

            statement.executeUpdate();
        }
    }

    @Override
    public void deletePeople(People people) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        System.out.println("БД подключена!");
        Statement statement = connection.createStatement();

        statement.executeUpdate("delete from people WHERE id = " + people.getId() + ";");
        statement.close();

        System.out.println("Данные из таблиц удалены");

        connection.close();
        System.out.println("Соединения закрыты");
    }
}
