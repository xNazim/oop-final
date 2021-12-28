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

    int index = -1;
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    private TableColumn<StudentsModel, String> eMail;

    @FXML
    private TableColumn<StudentsModel, String> firstName;

    @FXML
    private TableColumn<StudentsModel, String> lastName;

    @FXML
    private TableColumn<StudentsModel, String> studentId;

    @FXML
    private TableView<StudentsModel> tbData;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtsname;





    @FXML
    void getSelected(MouseEvent event){
        index = tbData.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtid.setText(studentId.getCellData(index));
        txtname.setText(firstName.getCellData(index).toString());
        txtsname.setText(lastName.getCellData(index).toString());
        txtemail.setText(eMail.getCellData(index).toString());

    }

    @FXML
    void DelStudent(ActionEvent event) {

        Integer sid = Integer.valueOf(txtid.getText());




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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UpdateTable();




    }



}
