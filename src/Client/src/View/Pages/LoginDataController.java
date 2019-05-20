package View.Pages;

import Domain.ClientManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;


public class LoginDataController {
    private ClientManager client;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton signIn;

    @FXML
    private void handleSignIn() throws RemoteException {
        String us = username.getText();
        String pass = password.getText();

        int esito = client.loginGUI(us,pass);

        if(esito == 1) {

        }
        if(esito == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Login");
            alert.setHeaderText("Username and Password doesn't exist!");

            alert.showAndWait();
        }
        if(esito == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Login");
            alert.setHeaderText("Alredy Logged!");
            alert.setContentText("Someone is alredy logged in your account,if you believe it is an unauthorized access contact the system's manager ");

            alert.showAndWait();
        }

    }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }
}
