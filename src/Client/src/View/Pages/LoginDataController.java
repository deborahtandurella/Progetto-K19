package View.Pages;

import Domain.ClientManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
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
    private JFXButton signUp;

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
            alert.setTitle("Error SignUp");
            alert.setHeaderText("Invalid Password");
            alert.setContentText("Someone is alredy logged in your account,if you believe it is an unauthorized access contact the system's manager ");

            alert.showAndWait();
        }

    }

    @FXML
    private void changeSceneSignUp() throws RemoteException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));

        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ((SignUpController)loader.getController()).setClient(client);

        //Scene scene = new Scene(loader.load());
        //stage.setScene(scene);


        //FXMLLoader loader = new FXMLLoader(getClass().getResource("./Pages/Login.fxml"));
        //Stage stage = new Stage();
        //stage.initOwner(signUp.getScene().getWindow());
        //stage.setScene(new Scene((Parent) loader.load()));

        //((SignUpController)loader.getController()).setClient(client);

        // showAndWait will block execution until the window closes...
        //stage.showAndWait();

        //SignUpController controller = loader.getController();
        //text1.setText(controller.getText());
    }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }
}
