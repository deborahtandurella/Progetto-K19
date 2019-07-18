package Client.Controller;

import Client.Domain.ClientManager;
import Server.People.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class UserPageController extends TemplateController {

    private TitleController titleController;

    private User user;
    @FXML
    private AnchorPane windowsPane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField newEmail;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField passwordOld;

    @FXML
    private JFXPasswordField passwordNew;

    @FXML
    private JFXPasswordField passwordRepeat;

    @FXML
    private JFXButton changeEmailButton;

    @FXML
    private JFXButton changePwsButton;

    @FXML
    private FontAwesomeIconView backButton;

    @FXML
    void changeEmail() throws RemoteException{
        String emailChanged= newEmail.getText();
        String psw = password.getText();
        if (user.checkPassword(psw)){
            int esito = client.changeEmail(emailChanged,user.getUsername());
            if(esito == 1) {
                String title="Success";
                String message ="Email changed succesfully";
                ControllerServices.getInstance().showAlert(title,message,primaryStage,Alert.AlertType.INFORMATION);
            }
            if(esito == -1) {
                String title="Error e-mail";
                String message ="E-mail is not valid";
                ControllerServices.getInstance().showAlert(title,message,primaryStage,Alert.AlertType.WARNING);
            }
        }
        else{
            String title="Wrong Password";
            String message ="Password entered incorrectly";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.ERROR);
        }
    }

    @FXML
    void changePws() throws RemoteException{
        String newPass = passwordNew.getText();
        String oldPass = passwordOld.getText();
        String repPass = passwordRepeat.getText();

        if (user.checkPassword(oldPass)){
            int esito = client.changePassword(newPass,repPass,user.getUsername());
            if(esito == 1) {
                String title="Success";
                String message ="Password changed succesfully";
                ControllerServices.getInstance().showAlert(title,message,primaryStage,Alert.AlertType.INFORMATION);
            }
            if(esito == -1) {
                String title="Invalid Password";
                String message ="Password requires at least 8 characters. Password must contain lowercase and uppercase letter,numbers and at least one special character";
                ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.WARNING);
            }
            if(esito == -2) {
                String title="Password doesn't match";
                String message = "New passwords don't match";
                ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.WARNING);
            }
        }
        else{
            String title="Wrong Password";
            String message ="Password entered incorrectly";
            ControllerServices.getInstance().showAlert(title,message,popUpStage,Alert.AlertType.ERROR);
        }
    }

    public void initializeNow() throws RemoteException {
        user = client.getUser();
        username.setText(user.getUsername());
        username.setEditable(false);
        email.setText(user.getEmail());
        email.setEditable(false);
    }

    void initializeWindow() {
        popUpStage.getScene().setFill(Color.TRANSPARENT);
        windowsPane.setStyle(

                "-fx-background-insets: 5; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );
    }

    @FXML
    private void backToLoginScreen() {
        AnchorPane pane = (AnchorPane) primaryStage.getScene().lookup("#windowsPane");
        pane.setEffect(null);
        popUpStage.close();
        titleController.setVisibleButtons();

    }

    @FXML
    public void handleCursorHand() {
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor() {
        popUpStage.getScene().setCursor(Cursor.DEFAULT);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TitleController getTitleController() { return titleController; }

    public void setTitleController(TitleController titleController) { this.titleController = titleController; }
}
