package View.Pages;

import Domain.ClientManager;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.rmi.RemoteException;

public class HomeController {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private AnchorPane principalPanel;

    @FXML
    private void changeSceneSignUp() throws RemoteException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent root = (Parent) loader.load();

        Stage popUpStage = new Stage(StageStyle.TRANSPARENT);
        popUpStage.initOwner(primaryStage);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setScene(new Scene(root));
        popUpStage.show();

        ((SignUpController)loader.getController()).setClient(client);
        ((SignUpController)loader.getController()).setPopUpStage(popUpStage);

    }

    @FXML
    private void exitFromAH () throws RemoteException, IOException {
        System.exit(0);
    }

    @FXML
    private void changeSceneLogIn() throws RemoteException, IOException {

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent) loader.load();

        Scene loginScene = new Scene(root);

        Stage changeStage = (Stage)((Node ).getSource()).getScene().getWindow();
        changeStage.setScene(loginScene);
        changeStage.show();*/

        //((LoginDataController)loader.getController()).setClient(client);
        //((LoginDataController)loader.getController()).setPrimaryStage(changeStage);  QUESTO NON E' SETTATO PERCHE BISOGNA DISCUTERE SE PARTIRE DALLA SCHERMATA DI LOGIN O DALLA HOME

    }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }
}