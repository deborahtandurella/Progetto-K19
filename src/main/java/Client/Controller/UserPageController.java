package Client.Controller;

import Client.Domain.ClientManager;
import Server.People.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class UserPageController {
    private ClientManager client;
    private Stage primaryStage;
    private Stage popUpStage;

    @FXML
    private AnchorPane windowsPane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField email;

    private User user;

    @FXML
    private JFXButton changeEmailButton;

    @FXML
    private JFXButton changePwsButton;

    @FXML
    private FontAwesomeIconView backButton;

    @FXML
    void changeEmail() {

    }

    @FXML
    void changePws() {


    }

    public void initializeNow() throws RemoteException {
        user = client.getUser();
        username.setText(user.getUsername());
        username.setEditable(false);
        email.setText(user.getE_mail());
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

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public Stage getPopUpStage() { return popUpStage; }

    public void setPopUpStage(Stage popUpStage) { this.popUpStage = popUpStage; }
}
