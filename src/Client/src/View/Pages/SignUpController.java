package View.Pages;

import Domain.ClientManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.rmi.RemoteException;

public class SignUpController {
    private ClientManager client;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private void handleRegistration() throws RemoteException {
        String us = username.getText();
        String pass = password.getText();

        int esito = client.signUpGUI(us,pass);

        if(esito == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User create successfully");

            alert.showAndWait();
        }
        if(esito == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Login");
            alert.setHeaderText("Username alredy exists");

            alert.showAndWait();
        }
        if(esito == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Login");
            alert.setHeaderText("Alredy Logged!");
            alert.setContentText("Use at least 1.....ELENCO PARAMETRI PASS");

            alert.showAndWait();
        }
    }

    @FXML
    private void backToLoginScreen() {

    }






    public ClientManager getClient() {
        return client;
    }

    public void setClient(ClientManager client) {
        this.client = client;
    }
}
