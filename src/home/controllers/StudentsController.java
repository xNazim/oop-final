package home.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {

    @FXML
    private TableView<StudentsModel> tbData;
    @FXML
    public TableColumn<StudentsModel, String> studentId;

    @FXML
    public TableColumn<StudentsModel, String> firstName;

    @FXML
    public TableColumn<StudentsModel, String> lastName;

    @FXML
    public TableColumn<StudentsModel, String> eMail;


    ObservableList<StudentsModel> objlist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con = JavaPostgreSql.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM student;");
            while (rs.next()) {
                objlist.add(new StudentsModel(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email") ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        tbData.setItems(objlist);
    }






}
