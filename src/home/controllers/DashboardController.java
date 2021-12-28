package home.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TableView<StudentsModel> tbData;
    @FXML
    public TableColumn<StudentsModel, Integer> studentId;

    @FXML
    public TableColumn<StudentsModel, String> firstName;

    @FXML
    public TableColumn<StudentsModel, String> lastName;

    @FXML
    private PieChart pieChart;

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

        loadChart();
        loadStudents();
    }

    private void loadChart()
    {

        PieChart.Data slice1 = new PieChart.Data("Total students", 100);
        PieChart.Data slice2 = new PieChart.Data("Total Hours"  , 200);
        PieChart.Data slice3 = new PieChart.Data("Total Lessons" , 8);



        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);

    }



    private void loadStudents()
    {
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tbData.setItems(objlist);
    }

}
