package View.Pages;

import Domain.ClientManager;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideOutRight;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class HomeController {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private AnchorPane header;

    @FXML
    private AnchorPane auctionList;

    @FXML AnchorPane windowsPane;

    @FXML
    private JFXButton createAu;


    @FXML
    public void createAuctionAction(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3,3,3);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormCreaAsta.fxml"));
        Parent root = (Parent) loader.load();

        Stage popUpStage = new Stage(StageStyle.TRANSPARENT);
        popUpStage.initOwner(primaryStage);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setScene(new Scene(root));
        popUpStage.show();

        windowsPane.setEffect(blur);



        //Animation
        new FadeIn(root).play();


        ((CreaAstaController)loader.getController()).setClient(client);
        ((CreaAstaController)loader.getController()).setPopUpStage(popUpStage);
        ((CreaAstaController)loader.getController()).setPrimaryStage(primaryStage);
    }





    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage; }

    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) {
        this.client = client;
        initializeHeader();
        initializeAuctionList();
    }

    public Stage getPrimaryStage() { return primaryStage; }

    private void initializeHeader() {
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

    private void initializeAuctionList() {
        FXMLLoader fxml = null;
        Parent root = null;

        try {
            fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("./AuctionList.fxml"));
            root = (Parent)fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        auctionList.getChildren().setAll(root);

        ((AuctionListController)fxml.getController()).setPrimaryStage(primaryStage);
        ((AuctionListController)fxml.getController()).setClient(client);
    }






}