package View.Pages2;

import Domain.ClientManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class SignUpController {
    private ClientManager client;
    private Stage popUpStage;

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
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Username alredy exists");

            alert.showAndWait();
        }
        if(esito == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Error ");
            alert.setContentText("Use at least 1.....ELENCO PARAMETRI PASS");

            alert.showAndWait();
        }
    }

    @FXML
    private void backToLoginScreen() {
        popUpStage.hide();
    }


    public void setPopUpStage(Stage popUpStage) {
        this.popUpStage = popUpStage;
    }

    public ClientManager getClient() {
        return client;
    }

    public void setClient(ClientManager client) {
        this.client = client;
    }
}
