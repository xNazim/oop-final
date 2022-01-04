package home.controllers;

import home.JavaPostgreSql;
import home.models.StudentsModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {


    @FXML
    private TableView<StudentsModel> tbData;
    @FXML
    public TableColumn<StudentsModel, Integer> studentId;

    @FXML
    public TableColumn<StudentsModel, String> firstName;

    @FXML
    public TableColumn<StudentsModel, String> lastName;

    @FXML
    public TableColumn<StudentsModel, String> eMail;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_sname;

    @FXML
    private TextField searchUp;

    ObservableList<StudentsModel> listM;
    ObservableList<StudentsModel> dataList;

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

    int index = -1;
    PreparedStatement pst = null;



    public void EditStudents(){
        try {
            Connection con = JavaPostgreSql.getConnection();
            Integer val1 = Integer.valueOf(txt_id.getText());
            String val2 = txt_name.getText();
            String val3 = txt_sname.getText();
            String val4 = txt_email.getText();

            String sql;
            sql = "UPDATE student SET first_name = '"+val2+"', last_name = '"+
                    val3+"', email = '"+val4+"' WHERE student_id =  "+val1+" ";

            pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Edited");
            UpdateTable();
            SearchUser();
        }   catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null, throwables);


        }
    }




    public  void UpdateTable(){

        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        listM = JavaPostgreSql.getDatastudents();
        tbData.setItems(listM);


    }

    public void SearchUser(){
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        dataList = JavaPostgreSql.getDatastudents();
        tbData.setItems(dataList);
        FilteredList<StudentsModel> filteredData = new FilteredList<>(dataList, b -> true);
        searchUp.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
        SortedList<StudentsModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbData.comparatorProperty());
        tbData.setItems(sortedData);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateTable();
        SearchUser();


    }



}
