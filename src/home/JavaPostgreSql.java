package home;

import home.models.StudentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class JavaPostgreSql {

    Connection connection = null;

    public static Connection getConnection() {
        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/students",
                    "username",
                    "password");

            return connection;
            } catch (Exception e){
            return null;
        }
    }

    public static ObservableList<StudentsModel> getDatastudents(){
        Connection connection = getConnection();
        ObservableList<StudentsModel> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new StudentsModel(Integer.parseInt(rs.getString("student_id")), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email")));
            }
        } catch (Exception e){

        }

        return list;

    }

}




