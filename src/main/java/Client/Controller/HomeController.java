package Client.Controller;

import Client.Domain.ClientManager;

import animatefx.animation.FadeIn;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HomeController extends TemplateController {

    private FXMLLoader fxml = null;

    @FXML
    private AnchorPane header;

    @FXML
    private AnchorPane auctionList;

    @FXML
    private AnchorPane windowsPane;

    @FXML
    private JFXButton createAu;

    @FXML
    private JFXButton search;

    @FXML
    private JFXTextField searchText;

    private TitleController titleController;

    private AuctionListController auctionListController;

    private CreateAuctionFormController auctionFormController;

    public void init() {
        initializeAuctionList();
        initializeHeader();
    }


    @FXML
    public void createAuctionAction(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3,3,3);

        Stage popUpStageCreate = loadScenePopUp("/View/CreateAuctionForm.fxml");

        windowsPane.setEffect(blur);

        auctionFormController = loader.getController();

        auctionFormController.setClient(client);
        auctionFormController.setPopUpStage(popUpStageCreate);
        auctionFormController.setPrimaryStage(primaryStage);
        auctionFormController.disableModifyDeleteAuction();

    }

    @FXML
    public void reloadLatestAuction() {

        auctionListController.refreshList();
        titleController.setVisibleButtons();
    }

    @FXML
    public void searchAuction() {
        if(searchText == null) {
            reloadLatestAuction();
        }
        else {
            String text = searchText.getText();

            auctionListController.searchList(text);

            titleController.setVisibleButtons();
        }
        new Pulse(search).setSpeed(2).play();
    }

    @FXML
    public void handleCursorHand(MouseEvent me) {
        primaryStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor(MouseEvent me) {
        primaryStage.getScene().setCursor(Cursor.DEFAULT);
    }


    private void initializeHeader() {
        FXMLLoader fxml = null;
        Parent root = null;

        try {
            fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/View/Title.fxml"));
            root = (Parent)fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        header.getChildren().setAll(root);

        titleController = (TitleController) fxml.getController();

        titleController.setPrimaryStage(primaryStage);
        titleController.setClient(client);
        titleController.setAuctionListController(auctionListController);

        //Inietto dipendenza
        auctionListController.setTitleController(titleController);

        titleController.setHomeController(this);

    }

    private void initializeAuctionList() {
        fxml = null;
        Parent root = null;

        try {
            fxml = new FXMLLoader();
            fxml.setLocation(getClass().getResource("/View/AuctionList.fxml"));
            root = (Parent)fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        auctionList.getChildren().setAll(root);

        auctionListController = (AuctionListController) fxml.getController();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAuctionForm.fxml"));

        auctionListController.setPrimaryStage(primaryStage);
        auctionListController.setClient(client);
        auctionListController.refreshList();
        auctionListController.setAuctionListController(auctionListController);
        auctionListController.setAuctionFormController((CreateAuctionFormController) loader.getController());
    }

    public TitleController getTitleController() {
        return titleController;
    }

    public void setTitleController(TitleController titleController) {
        this.titleController = titleController;
    }

    public AuctionListController getAuctionListController() {
        return auctionListController;
    }

    public void setAuctionListController(AuctionListController auctionListController) {
        this.auctionListController = auctionListController;
    }

    public AnchorPane getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(AnchorPane auctionList) {
        this.auctionList = auctionList;
    }
}