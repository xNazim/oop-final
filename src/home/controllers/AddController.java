package home.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddController implements Initializable {


    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtsname;

    @FXML
    private TextField txtemail;


    @FXML
    private Button btnadd;

    Connection con;
    PreparedStatement pst;


    @FXML
    void AddRecord(ActionEvent event) {

        Integer sid = Integer.valueOf(txtid.getText());
        String sname = txtname.getText();
        String lname = txtsname.getText();
        String eemail = txtemail.getText();


        try {
            Connection con = JavaPostgreSql.getConnection();

            pst = con.prepareStatement("INSERT INTO student(student_id, first_name, last_name, email) VALUES (?, ?, ?, ?)");
            pst.setInt(1, sid);
            pst.setString(2, sname);
            pst.setString(3, lname);
            pst.setString(4, eemail);

            int satus = pst.executeUpdate();

            if (satus == 1)
            {
                JOptionPane.showMessageDialog(null, "Student added");

                txtid.setText("");
                txtname.setText("");
                txtsname.setText("");
                txtid.requestFocus();
            }
            else {
                JOptionPane.showMessageDialog(null, "Adding Failed");

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
