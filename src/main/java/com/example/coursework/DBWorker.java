package com.example.coursework;

import java.sql.*;
import java.util.ArrayList;

import com.example.coursework.*;
import javafx.collections.ObservableList;

public class DBWorker implements Repository{
    ArrayList<People> list = new ArrayList<People>();
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

//    public Connection getDbConnection()  {
//        try {
//            connection = DriverManager.getConnection(jdbUrl);
//        }
//        catch (SQLException e){
//            System.out.println("Ошибка с подключением бд");
//            e.printStackTrace();
//        }
//        return connection;
//    }
    public static void createTable(){
        try {
            Statement statement=connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'people'('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'name' text, 'patronymic' text, 'nickname' text, 'dataOfBirth' text, 'dateOfDeath' text, 'gender' text, 'photo' text, 'info' text);");
            System.out.println("Таблицы успешно созданы");

//            statement.execute("INSERT INTO 'people'('id', 'surname', 'name', 'patronymic', 'nickname', 'dataOfBirth', 'dateOfDeath', 'gender', 'photo', 'info') VALUES (3, 'Гуруева', 'Наталья', 'Олеговна', 'Наташа', '30.04.2004', '', 'жен', '', '')");

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

        Statement statement = connection.createStatement();
            statement.executeUpdate("update people set surname='"+people.getSurname()+ "', name = '" + people.getName() + "', patronymic = '" + people.getPatronymic() + "', nickname = '" + people.getNickname() + "', dataOfBirth = '" + people.getDataOfBirth() + "', dateOfDeath = '" + people.getDateOfDeath() + "', gender = '" + people.getGender() + "', photo = '" + people.getPhoto() + "', info = '" + people.getInfo() + "' where id="+people.getId()+" ;");
            System.out.println("данные о человеке изменены");


        statement.close();

        connection.close();
        System.out.println("Соединения закрыты");
    }
//    public ResultSet getAllPeople() {
//        String getPeople="SELECT * FROM people";
//        PreparedStatement prSt = null;
//        try{
//            prSt = getDbConnection().prepareStatement(getPeople);
//            resultSet = prSt.executeQuery();
//        }
//        catch (SQLException e){
//            System.out.println("Ошибка с подключением бд");
//            e.printStackTrace();
//        }
//
//        return resultSet;
//    }

//    @Override
//    public ObservableList<People> getAllPeople() throws SQLException {
//        ObservableList<People> list = null;
//        Connection conn = DriverManager.getConnection(jdbUrl);
//        Statement statement = conn.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM people;");
//        while(resultSet.next()) {
//            list.add(new People(resultSet.getInt("id"), resultSet.getString("surname"), resultSet.getString("name"), resultSet.getString("patronymic"), resultSet.getString("nickname"), resultSet.getString("dataOfBirth"), resultSet.getString("dateOfDeath"), resultSet.getString("gender"), resultSet.getString("photo"), resultSet.getString("info")));
//        }
//
//        resultSet.close();
//        return list;
//    }



    public Connection getDbConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(jdbUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
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

    public People getPerson(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(jdbUrl);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM people WHERE id = " + id + ";");

        People person = new People(resultSet.getInt("id"), resultSet.getString("surname"), resultSet.getString("name"), resultSet.getString("patronymic"), resultSet.getString("nickname"), resultSet.getString("dataOfBirth"), resultSet.getString("dateOfDeath"), resultSet.getString("gender"), resultSet.getString("photo"), resultSet.getString("info"));

        return person;
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

    public void truncateTable(String tableName) throws SQLException {
        String sql = String.format("TRUNCATE TABLE %s", tableName);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }


//    @Override
//    public ArrayList<PrinteredEdition> getAllEditions() throws SQLException {
//        ArrayList<PrinteredEdition> list = new ArrayList<PrinteredEdition>();
//        Connection conn = DriverManager.getConnection(URL);
//        Statement statement = conn.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book;");
//        while(resultSet.next()) {
//            list.add(new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), resultSet.getString("genre")));
//        }
//        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Textbook;");
//        while(resultSet1.next()) {
//            list.add(new Textbook(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), resultSet.getString("genre"), resultSet.getString("StudyArea")));
//        }
//
//        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Magazine;");
//        while(resultSet2.next()) {
//            list.add(new Magazine(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getInt("numOfPages"), 1));
//        }
//
//        resultSet.close();
//        resultSet1.close();
//        resultSet2.close();
//        count = list.size();
//        return list;
//    }
}
