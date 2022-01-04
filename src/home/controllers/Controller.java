package home.controllers;

import home.JavaPostgreSql;
import home.models.StudentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {



        @FXML
        private Button btnAdd;

        @FXML
        private Button btnClasses;

        @FXML
        private Button btnDelete;

        @FXML
        private Button btnStudents;

        @FXML
        private Button btn_Timetable;

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
        private Button refBtn;

        @FXML
        private TextField searchHome;



    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnClasses) {
            loadStage("/home/fxml/Classes.fxml");
        } else if (mouseEvent.getSource() == btnStudents) {
            loadStage("/home/fxml/Update.fxml");
        } else if (mouseEvent.getSource() == btn_Timetable) {
            loadStage("/home/fxml/Timetable.fxml");
        } else if (mouseEvent.getSource() == btnAdd) {
            loadStage("/home/fxml/Add.fxml");
        } else if (mouseEvent.getSource() == btnDelete) {
            loadStage("/home/fxml/Delete.fxml");
        }
    }


    @FXML
    void refreshList(ActionEvent event) {

        UpdateTable();

    }





    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/home/images/logoiau.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        searchHome.textProperty().addListener((observableValue, oldValue, newValue) -> {
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





    ObservableList<StudentsModel> objlist;
    ObservableList<StudentsModel> dataList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateTable();
        loadStudents();
        SearchUser();
    }



    private void loadStudents()
    {
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tbData.setItems(objlist);
    }

}
