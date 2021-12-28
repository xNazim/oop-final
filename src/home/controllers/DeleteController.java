package home.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    Connection con;
    PreparedStatement pst;

    @FXML
    private TableColumn<StudentsModel, String> firstName;

    @FXML
    private TableColumn<StudentsModel, String> lastName;

    @FXML
    private TableColumn<StudentsModel, String> studentId;

    @FXML
    private TableView<StudentsModel> tbData;

    @FXML
    private TextField txtid;

    @FXML
    void DelStudent(ActionEvent event) {

        Integer sid = Integer.valueOf(txtid.getText());




        try {
            Connection con = JavaPostgreSql.getConnection();

            String sql = "DELETE FROM student where student_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, sid);

            int satus = pst.executeUpdate();



            if (satus == 1)
            {
                JOptionPane.showMessageDialog(null, "deleted");

                UpdateTable();




                txtid.setText("");
            }
            else {
                JOptionPane.showMessageDialog(null, "Deletion Failed");

            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }






    }
    ObservableList<StudentsModel> objlist = FXCollections.observableArrayList();
    public void UpdateTable(){

        try {
            Connection con = JavaPostgreSql.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM student;");
            while (rs.next()) {
                objlist.add(new StudentsModel(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name") ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));

        tbData.setItems(objlist);


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UpdateTable();




    }



}
