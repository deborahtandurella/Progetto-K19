package Client.Controller;

import Client.Exceptions.EmailInvalidException;
import Client.Exceptions.EmailTakenException;
import Client.Exceptions.PasswordTakenException;
import Client.Exceptions.UsernameTakenException;
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
    private void handleRegistration() throws RemoteException, UsernameTakenException, PasswordTakenException, EmailInvalidException, EmailTakenException {
        try {
        String us = username.getText();
        String emailText = email.getText();
        String pass = password.getText();

        client.signUpGUI(us,pass,emailText);

        String title="Information Dialog";
        String header="Welcome "+us+"!";
        String message ="User created successfully";
        ControllerServices.getInstance().showAlert(title,header,message,popUpStage,Alert.AlertType.INFORMATION);
        }
        catch (UsernameTakenException ute) {
            String title="Error SignUp";
            String header="Username already taken";
            String message ="Username already exists";
            ControllerServices.getInstance().showAlert(title,header,message,popUpStage,Alert.AlertType.ERROR);
        }
        catch (PasswordTakenException pte) {
            String title="Error SignUp";
            String header="Follow the rules!";
            String message ="Password requires at least 8 characters. Password must contain lowercase and uppercase letter,numbers and at least one special character";
            ControllerServices.getInstance().showAlert(title,header,message,popUpStage,Alert.AlertType.WARNING);
        }
        catch (EmailInvalidException eie){
            String title="Error SignUp";
            String header="Invalid input";
            String message ="Email is not valid";
            ControllerServices.getInstance().showAlert(title,header,message,popUpStage,Alert.AlertType.WARNING);
        }
        catch (EmailTakenException ete) {
            String title="Error SignUp";
            String header="Change your email";
            String message ="Email already taken";
            ControllerServices.getInstance().showAlert(title,header,message,popUpStage,Alert.AlertType.WARNING);
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
