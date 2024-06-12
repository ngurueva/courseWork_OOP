package com.example.coursework.view;

import com.example.coursework.data.*;
import com.example.coursework.db.DBWorker;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import static com.example.coursework.HelloController.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddWindow implements Initializable {
    private DBWorker dbWorker = new DBWorker();
    public ImageView imgPerson;
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldNickname;
    public DatePicker textFieldDateOfBirth;
    public DatePicker textFieldDateOfDeath;
    private String gender;
    public ComboBox<String> genderComboBox = new ComboBox<>();
    public ComboBox comboBoxMom;
    public ComboBox comboBoxDad;
    public ComboBox comboBoxSpouse;
    public TextArea textFieldInfo;
    public Button BtnAdd;
    private People person;
    private String filePath;
    private Stage stage = new Stage();
    private boolean flag = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderComboBox.getItems().addAll("муж", "жен");
        genderComboBox.setValue("муж");
        try {
            dbWorker = new DBWorker();
            ObservableList<String> moms = FXCollections.observableArrayList();
            ResultSet resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                moms.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxMom.setItems(moms);

            ObservableList<String> dads = FXCollections.observableArrayList();
            resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                dads.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxDad.setItems(dads);

            ObservableList<String> spouses = FXCollections.observableArrayList();
            resultSet = dbWorker.getPeopleData();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                spouses.add("id " + id + ": " + name + " " + surname);
            }
            comboBoxSpouse.setItems(spouses);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void openAddWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image("file:C:/Users/Наталья/Downloads/free-icon-tree-4319592.png"));
        stage.setTitle("Добавление");
        stage.setMinWidth(320);
        stage.setMinHeight(650);
        stage.setMaxWidth(320);
        stage.setMaxHeight(650);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            windowOpen = false;
        });
        stage.show();
    }

    public void add() throws SQLException {

            imgPerson.setImage(new Image("file:C:/Users/Наталья/IdeaProjects/courseWork/src/main/resources/com/example/coursework/icons/noImg.png"));
            String surnameText = textFieldSurname.getText();
            String nameText = textFieldName.getText();
            String patronymicText = textFieldPatronymic.getText();
            String nicknameText = textFieldNickname.getText();

            String dateOfBirthText = "";
            String dateOfDeathText = "";

        if ((textFieldDateOfBirth.getValue() != null) && (textFieldDateOfBirth.getValue().toString().matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"))) {
            dateOfBirthText = textFieldDateOfBirth.getValue().toString();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат даты рождения", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if ((textFieldDateOfDeath.getValue() != null) && (textFieldDateOfBirth.getValue().toString().matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"))) {
            dateOfDeathText = textFieldDateOfDeath.getValue().toString();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат даты смерти", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        gender = genderComboBox.getValue();
            String infoText = textFieldInfo.getText();
            newId++;
            person = new People(newId, surnameText, nameText, patronymicText, nicknameText, dateOfBirthText, dateOfDeathText, gender, filePath, infoText);
            dbWorker.addPeople(person);

            if (comboBoxMom.getValue() != null) {
                String comboBoxMomValue = (String) comboBoxMom.getValue();
                int momId = Integer.parseInt(comboBoxMomValue.substring(comboBoxMomValue.indexOf("id ") + 3, comboBoxMomValue.indexOf(":")));

                dbWorker.addRelative(idRelative, newId, momId, "мать");
                Kinship kinship = new Kinship(idRelative, newId, momId, "мать");
                kinshipObservableList.add(kinship);
                idRelative++;
                dbWorker.addRelative(idRelative, momId, newId, "ребенок");
                Kinship kinship1 = new Kinship(idRelative, momId, newId, "ребенок");
                kinshipObservableList.add(kinship1);
                idRelative++;
            }

            if (comboBoxDad.getValue() != null) {
                String comboBoxDadValue = (String) comboBoxDad.getValue();
                int dadId = Integer.parseInt(comboBoxDadValue.substring(comboBoxDadValue.indexOf("id ") + 3, comboBoxDadValue.indexOf(":")));

                dbWorker.addRelative(idRelative, newId, dadId, "отец");
                Kinship kinship2 = new Kinship(idRelative, newId, dadId, "отец");
                kinshipObservableList.add(kinship2);
                idRelative++;
                dbWorker.addRelative(idRelative, dadId, newId, "ребенок");
                Kinship kinship3 = new Kinship(idRelative, dadId, newId, "ребенок");
                kinshipObservableList.add(kinship3);
                idRelative++;
            }

            if ((comboBoxSpouse.getValue() != null) && (Objects.equals(gender, "жен"))) {
                String comboBoxSpouseValue = (String) comboBoxSpouse.getValue();
                int spouseId = Integer.parseInt(comboBoxSpouseValue.substring(comboBoxSpouseValue.indexOf("id ") + 3, comboBoxSpouseValue.indexOf(":")));

                dbWorker.addRelative(idRelative, newId, spouseId, "муж");
                Kinship kinship4 = new Kinship(idRelative, newId, spouseId, "муж");
                kinshipObservableList.add(kinship4);
                idRelative++;
                dbWorker.addRelative(idRelative, spouseId, newId, "жена");
                Kinship kinship5 = new Kinship(idRelative, spouseId, newId, "жена");
                kinshipObservableList.add(kinship5);
                idRelative++;
            } else if ((comboBoxSpouse.getValue() != null) && (Objects.equals(gender, "муж"))) {
                String comboBoxSpouseValue = (String) comboBoxSpouse.getValue();
                int spouseId = Integer.parseInt(comboBoxSpouseValue.substring(comboBoxSpouseValue.indexOf("id ") + 3, comboBoxSpouseValue.indexOf(":")));

                dbWorker.addRelative(idRelative, newId, spouseId, "жена");
                Kinship kinship5 = new Kinship(idRelative, newId, spouseId, "жена");
                kinshipObservableList.add(kinship5);
                idRelative++;
                dbWorker.addRelative(idRelative, spouseId, newId, "муж");
                Kinship kinship4 = new Kinship(idRelative, spouseId, newId, "муж");
                kinshipObservableList.add(kinship4);
                idRelative++;
            }

            observablePeopleList.add(person);
            windowOpen = false;
            stage.close();


    }
    public void addImg() {
        if (flag == true){
            flag = false;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображения", "*.jpg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(imageFilter);
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                filePath = file.getAbsolutePath();
                Image image = new Image("file:" + filePath);
                imgPerson.setImage(image);
            }
            flag = true;
        }
    }

}
