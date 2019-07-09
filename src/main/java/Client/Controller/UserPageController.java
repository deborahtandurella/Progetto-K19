package Client.Controller;

import Client.Domain.ClientManager;
import Server.People.User;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class UserPageController {

    @FXML
    private Label username;

    @FXML
    private Label email;

    private User user;
    private ClientManager clientManager;
    private Stage primaryStage;
    @FXML
    private JFXButton changeEmailButton;

    @FXML
    private JFXButton changePwsButton;

    @FXML
    void changeEmail(ActionEvent event) {

    }

    @FXML
    void changePws(ActionEvent event) {

    }

    public void initializeNow(){
        username.setText(user.getUsername());
        email.setText(user.getE_mail());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
