package View.Pages;

import Domain.ClientManager;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeController {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private AnchorPane header;





    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage; }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) {
        this.client = client;
        initialize();
    }

    public Stage getPrimaryStage() { return primaryStage; }

    private void initialize() {
        FXMLLoader fxml = null;
        Parent root = null;

        try {
            fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("./Title.fxml"));
            root = (Parent)fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        header.getChildren().setAll(root);

        ((TitleController)fxml.getController()).setPrimaryStage(primaryStage);
        ((TitleController)fxml.getController()).setClient(client);
    }




}