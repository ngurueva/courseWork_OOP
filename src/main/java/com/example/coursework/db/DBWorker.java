package com.example.coursework.db;

import java.sql.*;
import java.util.ArrayList;
import com.example.coursework.data.*;

public class DBWorker implements Repository {
    private ArrayList<People> list = new ArrayList<People>();
    private ArrayList<Kinship> listKinship = new ArrayList<Kinship>();
    private Repository repository;
    public static String dbURL = "jdbc:sqlite:C:\\SQLite\\sqlite-tools-win-x64-3450200\\tree.db";
    private static Connection conn;

    public static void initDB(){
        try {
            conn = DriverManager.getConnection(dbURL);
            createTable();
        }
        catch (SQLException e){
            System.out.println("Ошибка с подключением бд");
            e.printStackTrace();
        }
    }
    public static void createTable(){
        try {
            Statement statement= conn.createStatement();
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
    public ArrayList<People> getAllPeople() throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people;");

        while(resultSet.next()) {
            list.add(new People(resultSet.getInt("id"), resultSet.getString("surname"), resultSet.getString("name"), resultSet.getString("patronymic"), resultSet.getString("nickname"), resultSet.getString("dataOfBirth"), resultSet.getString("dateOfDeath"), resultSet.getString("gender"), resultSet.getString("photo"), resultSet.getString("info")));
        }

        return list;
    }

    public ArrayList<Kinship> getAllKinship() throws SQLException{
        Connection conn = DriverManager.getConnection(dbURL);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM relatives;");

        while(resultSet.next()) {
            listKinship.add(new Kinship(resultSet.getInt("id"), resultSet.getInt("id_person"), resultSet.getInt("id_relative"), resultSet.getString("kinship")));
        }

        return listKinship;
    }
    public ResultSet getPeopleData() throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        String sql = "SELECT * FROM people";
        Statement statement = conn.createStatement();
        return statement.executeQuery(sql);
    }
    @Override
    public void addPeople(People people) throws SQLException
    {
        conn = DriverManager.getConnection(dbURL);
        Statement statement = conn.createStatement();
        statement.executeUpdate("insert into people values (" + people.getId() + ", '" + people.getSurname() + "', '" + people.getName() + "', '" + people.getPatronymic() + "', '" + people.getNickname() + "', '" + people.getDataOfBirth() + "', '" + people.getDateOfDeath() + "', '" + people.getGender() + "', '" + people.getPhoto() + "', '" + people.getInfo() + "');");
        System.out.println("people добавлен в БД");

        System.out.println("Соединения закрыты");
        conn.close();
    }

    @Override
    public void addRelative(int id, int people1, int people2, String kinship) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbURL);
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
    public void editRelative(Kinship kinship) throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        System.out.println("БД подключена!");

        try (PreparedStatement statement = conn.prepareStatement(
                "update relatives set id_person=?, id_relative=?, kinship=? where id=?")) {

            statement.setInt(1, kinship.getFirstPerson());
            statement.setInt(2, kinship.getSecondPerson());
            statement.setString(3, kinship.getRelatives());
            statement.setInt(4, kinship.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные о человеке изменены.");
            } else {
                System.out.println("Не удалось обновить данные о человеке.");
            }
        } finally {
            if (conn != null) {
                conn.close();
                System.out.println("Соединения закрыты");
            }
        }
    }

    public void editPeople(People people) throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        System.out.println("БД подключена!");

        try (PreparedStatement statement = conn.prepareStatement(
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
            if (conn != null) {
                conn.close();
                System.out.println("Соединения закрыты");
            }
        }
    }

//    @Override
//    public ResultSet getRelative(People people) throws SQLException {
//        conn = DriverManager.getConnection(dbURL);
//        String sql = "SELECT people.id AS id, people.surname || ' ' || people.name || ' ' || people.patronymic AS fio, relatives.kinship AS kinship FROM relatives JOIN people ON people.id = relatives.id_person where relatives.id_relative = " + people.getId();
//
//        Statement statement = conn.createStatement();
//        return statement.executeQuery(sql);
//    }

    @Override
    public ResultSet getRelative(People people) throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        String sql = "SELECT " +
                "  p.id AS id, " +
                "  p.surname || ' ' || p.name || ' ' || p.patronymic AS fio, " +
                "  CASE " +
                "    WHEN r.id_person = ? THEN r.kinship " +
                "    ELSE (SELECT kinship FROM relatives WHERE id_person = r.id_relative AND id_relative = ?) " +
                "  END AS kinship " +
                "FROM relatives r " +
                "JOIN people p ON p.id = CASE WHEN r.id_person = ? THEN r.id_relative ELSE r.id_person END " +
                "WHERE r.id_person = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, people.getId());
        statement.setInt(2, people.getId());
        statement.setInt(3, people.getId());
        statement.setInt(4, people.getId());
        return statement.executeQuery();
    }



    @Override
    public void deletePeople(People people) throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        System.out.println("БД подключена!");
        Statement statement = conn.createStatement();

        statement.executeUpdate("delete from people WHERE id = " + people.getId() + ";");
        statement.close();

        System.out.println("Данные из таблиц удалены");

        conn.close();
        System.out.println("Соединения закрыты");
    }
    @Override
    public void deleteRelative(People people) throws SQLException {
        conn = DriverManager.getConnection(dbURL);
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from relatives WHERE id_person = " + people.getId() + " or id_relative = " + people.getId() + ";");
        statement.close();
        System.out.println("Данные из таблиц удалены");
        conn.close();
        System.out.println("Соединения закрыты");
    }
}
