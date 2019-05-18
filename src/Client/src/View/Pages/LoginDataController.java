package View.Pages;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;


public class LoginDataController {
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton signIn;

    @FXML
    private void handleSignIn() {
        String us = username.getText();
        String pass = password.getText();
        System.out.println(us + pass);
    }


}
