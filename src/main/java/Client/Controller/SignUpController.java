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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User create successfully");
            alert.initOwner(popUpStage);
            alert.showAndWait();
        }
        if(esito == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Username alredy exists");
            alert.initOwner(popUpStage);

            alert.showAndWait();

        }
        if(esito == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Error ");
            alert.setContentText("Use at least 1.....ELENCO PARAMETRI PASS");
            alert.initOwner(popUpStage);

            alert.showAndWait();
        }
        if(esito == -2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Error ");
            alert.setContentText("Email non rispetta i parametri reali");
            alert.initOwner(popUpStage);

            alert.showAndWait();
        }

        if(esito == -3) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Error ");
            alert.setContentText("Email gia' in uso");
            alert.initOwner(popUpStage);

            alert.showAndWait();
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
