package Client.Controller;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;

public class SignUpController extends TemplateController {

    @FXML
    private JFXTextField username;
    
    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private FontAwesomeIconView backButton;

    @FXML
    private AnchorPane windowsPane;

    @FXML
    private void handleRegistration() throws RemoteException {
        String us = username.getText();
        String emailText = email.getText();
        String pass = password.getText();

        int esito = client.signUpGUI(us,pass,emailText);

        if(esito == 1) {
            String title="Information Dialog";
            String message ="User created successfully";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.INFORMATION);
            }
        if(esito == 0) {
            String title="Error SignUp";
            String message ="Username already exists";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.ERROR);
        }
        if(esito == -1) {
            String title="Error SignUp";
            String message ="Password requires at least 8 characters. Password must contain lowercase and uppercase letter,numbers and at least one special character";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.WARNING);
        }
        if(esito == -2) {
            String title="Error SignUp";
            String message ="Email is not valid";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.WARNING);
        }
        if(esito == -3) {
            String title="Error SignUp";
            String message ="Email already taken";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void backToLoginScreen() {
        AnchorPane pane = (AnchorPane) primaryStage.getScene().lookup("#windowsPane");
        pane.setEffect(null);
        popUpStage.close();
    }

    @FXML
    public void handleCursorHand(MouseEvent me) {
        backButton.setStyle("-fx-background-color:#dae7f3;");
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor(MouseEvent me) {
        popUpStage.getScene().setCursor(Cursor.DEFAULT);
        backButton.setStyle("-fx-background-color:transparent;");
    }


    void initializeWindow() {
        popUpStage.getScene().setFill(Color.TRANSPARENT);
        windowsPane.setStyle(

                "-fx-background-insets: 5; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );
    }
}
