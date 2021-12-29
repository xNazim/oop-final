package home.controllers;


import home.JavaPostgreSql;
import home.models.ClassesModel;
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

public class Classes implements Initializable {

    @FXML
    private TableColumn<ClassesModel, String> Lecturer;

    @FXML
    private TableColumn<ClassesModel, Integer> Lesson;

    @FXML
    private TableColumn<ClassesModel, Integer> class_Id;

    @FXML
    private TableColumn<ClassesModel, String> class_Name;

    @FXML
    private TableView<ClassesModel> tbClasses;

    ObservableList<ClassesModel> objlist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){

        try {
            Connection con = JavaPostgreSql.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM classes;");
            while (rs.next()){
                objlist.add(new ClassesModel(rs.getInt("class_id"), rs.getString("c_name"), rs.getString("lecturer"), rs.getInt("lesson_p_week")));
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        class_Id.setCellValueFactory(new PropertyValueFactory<>("classId"));
        class_Name.setCellValueFactory(new PropertyValueFactory<>("C_Name"));
        Lecturer.setCellValueFactory(new PropertyValueFactory<>("Lecturer"));
        Lesson.setCellValueFactory(new PropertyValueFactory<>("Lesson_p_Week"));

        tbClasses.setItems(objlist);


    }






}
