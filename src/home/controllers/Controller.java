package home.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        private Button btnDashboard;

        @FXML
        private Button btnDelete;

        @FXML
        private Button btnStudents;

        @FXML
        private Button btn_Timetable;

        @FXML
        private TableColumn<StudentsModel, String> firstName;

        @FXML
        private TableColumn<StudentsModel, String> lastName;

        @FXML
        private TableColumn<StudentsModel, Integer> studentId;

        @FXML
        private TableView<StudentsModel> tbData;





    //my bad - the freaking mouse event
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnDashboard) {
            loadStage("/home/fxml/Dashboard.fxml");
        } else if (mouseEvent.getSource() == btnStudents) {
            loadStage("/home/fxml/Students.fxml");
        } else if (mouseEvent.getSource() == btn_Timetable) {
            loadStage("/home/fxml/Timetable.fxml");
        } else if (mouseEvent.getSource() == btnAdd) {
            loadStage("/home/fxml/Add.fxml");
        } else if (mouseEvent.getSource() == btnDelete) {
            loadStage("/home/fxml/Delete.fxml");
        }
    }

    ObservableList<StudentsModel> objlist = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con = JavaPostgreSql.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM student;");
            while (rs.next()) {
                objlist.add(new StudentsModel(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name") ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        loadStudents();
    }



    private void loadStudents()
    {
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tbData.setItems(objlist);
    }



    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/home/icons/logoiau.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
