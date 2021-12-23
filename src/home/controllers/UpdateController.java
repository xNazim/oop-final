package home.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateController {

    @FXML
    private Button submitButton;
    @FXML
    private TextField id;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;

    public void getData(ActionEvent actionEvent){
        System.out.println(id.getText());
        System.out.println(first_name.getText());
        System.out.println(last_name.getText());

    }
}
