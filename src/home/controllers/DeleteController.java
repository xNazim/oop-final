package home.controllers;

import home.JavaPostgreSql;
import home.models.StudentsModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {

    @FXML
    private Button btnDel;

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<StudentsModel, String> eMail;

    @FXML
    private TableColumn<StudentsModel, String> firstName;

    @FXML
    private TableColumn<StudentsModel, String> lastName;

    @FXML
    private TableColumn<StudentsModel, Integer> studentId;

    @FXML
    private TableView<StudentsModel> tbData;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_sname;

    ObservableList<StudentsModel> objlist;
    ObservableList<StudentsModel> dataList;

    int index = -1;
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    void DelStudent(ActionEvent event) {

        Integer sid = Integer.valueOf(txt_id.getText());




        try {
            Connection con = JavaPostgreSql.getConnection();

            String sql = "DELETE FROM student where student_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, sid);

            int status = pst.executeUpdate();



            if (status == 1)
            {
                JOptionPane.showMessageDialog(null, "deleted");

                UpdateTable();
                SearchUser();



                txt_id.setText("");
            }
            else {
                JOptionPane.showMessageDialog(null, "Deletion Failed");

            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }



    @FXML
    public void getSelected(MouseEvent event){
        index = tbData.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }


        txt_id.setText(studentId.getCellData(index).toString());
        txt_name.setText(firstName.getCellData(index).toString());
        txt_sname.setText(lastName.getCellData(index).toString());
        txt_email.setText(eMail.getCellData(index).toString());

    }


    public  void UpdateTable(){

        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        objlist = JavaPostgreSql.getDatastudents();
        tbData.setItems(objlist);


    }

    public void SearchUser(){
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        dataList = JavaPostgreSql.getDatastudents();
        tbData.setItems(dataList);
        FilteredList<StudentsModel> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate(studentsModel -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String loweCaseFilter = newValue.toLowerCase();

                if (studentsModel.getFirstName().toLowerCase().indexOf(loweCaseFilter) != -1){
                    return true;
                } else if (studentsModel.getLastName().toLowerCase().indexOf(loweCaseFilter) != -1){
                    return  true;
                } else  if (String.valueOf(studentsModel.getEmail()).indexOf(loweCaseFilter) != -1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<StudentsModel>sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(sortedData);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UpdateTable();
        SearchUser();
    }



}
