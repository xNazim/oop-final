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
    void getSelected(MouseEvent event){
        index = tbData.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }


        txt_id.setText(studentId.getCellData(index).toString());
        txt_name.setText(firstName.getCellData(index).toString());
        txt_sname.setText(lastName.getCellData(index).toString());
        txt_email.setText(eMail.getCellData(index).toString());

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        objlist = JavaPostgreSql.getDatastudents();
        tbData.setItems(objlist);







    }



}
