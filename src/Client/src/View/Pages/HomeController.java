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


public class HomeController implements Initializable {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private AnchorPane header;





    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent fxml = null;

        try {
            fxml =  FXMLLoader.load(getClass().getResource("./Title.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        header.getChildren().setAll(fxml);

    }
}