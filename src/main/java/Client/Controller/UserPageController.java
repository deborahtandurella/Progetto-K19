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
        System.out.println(psw);
        System.out.println(user.getPassword());
        if (user.checkPassword(psw)){
            int esito = client.changeEmail(emailChanged,user.getUsername());
            if(esito == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Email changed succesfully");
                alert.initOwner(primaryStage);

                alert.showAndWait();
            }
            if(esito == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error e-mail");
                alert.setHeaderText("E-mail doesn't correspond the pattern ****@****.** ");
                alert.initOwner(primaryStage);
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong password");
            alert.setHeaderText("Wrong password");
            alert.initOwner(popUpStage);
            alert.showAndWait();
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Password changed succesfully");
                alert.initOwner(primaryStage);
                alert.showAndWait();
            }
            if(esito == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Password");
                alert.setHeaderText("Invalid Password");
                alert.initOwner(primaryStage);
                alert.showAndWait();
            }
            if(esito == -2) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Password doesn't match");
                alert.setHeaderText("New passwords don't match");
                alert.initOwner(primaryStage);
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong password");
            alert.setHeaderText("Wrong password");
            alert.initOwner(popUpStage);
            alert.showAndWait();
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
    public void handleCursorHand(MouseEvent me) {
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor(MouseEvent me) {
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
