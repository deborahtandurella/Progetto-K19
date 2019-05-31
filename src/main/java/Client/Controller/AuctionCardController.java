package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Optional;


public class AuctionCardController {
    private ClientManager client;
    private Stage popUpStage;
    private Auction auction;

    @FXML
    private Label auctionName;

    @FXML
    private Label higherOffer;

    @FXML
    private Label bidderHigher;

    @FXML
    private Label vendor;

    @FXML
    private ImageView auctionImage;

    @FXML
    private Label closeDate;

    @FXML
    private JFXButton offerButton;



    @FXML
    private Label timer;


    public void initializeNow() {
        auctionName.setText(auction.getLot().getDescription());
        if(auction.getLastBid() != null) {
            higherOffer.setText("$" + auction.getLastBid().getAmount());
            bidderHigher.setText(auction.getLastBid().getActorDB().getUsername());
        }
        else {
            higherOffer.setText("$"+auction.getHigherOffer());
            bidderHigher.setText("null");
        }

        vendor.setText(auction.getLot().getVendorDB().getUsername());
        closeDate.setText(auction.getClosingDate().toString());

        Image img;
        auctionImage = new ImageView();


        if(auction.getImage().exists()) {

                //img = new Image(new FileInputStream(auction.getImage()),100,100,false,false);
                //img = new Image((auction.getImage().toURI().toString()));
                //auctionImage.setImage(img);
            try {
                File file = null;
                try {
                    URL res = getClass().getClassLoader().getResource("Images/i_have_no_idea.png");
                    file = Paths.get(res.toURI()).toFile();
                }catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                //In alternativa a tutto quello sopra a partire dal try si puo' usare questo path: target/classes/Images/i_have_no_idea.png
                String absolutePath = file.getAbsolutePath();
                img = new Image(new FileInputStream(absolutePath), 100, 100, false, false);

                auctionImage = new ImageView();
                auctionImage.setImage(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                File file = null;
                try {
                    URL res = getClass().getClassLoader().getResource("Images/i_have_no_idea.png");
                    file = Paths.get(res.toURI()).toFile();
                }catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                //In alternativa a tutto quello sopra a partire dal try si puo' usare questo path: target/classes/Images/i_have_no_idea.png
                String absolutePath = file.getAbsolutePath();
                img = new Image(new FileInputStream(absolutePath), 100, 100, false, false);

                auctionImage = new ImageView();
                auctionImage.setImage(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000000),
                ae ->{} ));
        timeline.play();
        timer.setText(timeline.getCurrentTime()+"");

    }

    @FXML
    private void backToLoginScreen() {
        popUpStage.close();
    }

    @FXML
    private void makeAnOffer() throws RemoteException {
        if(client.getLoggedUser().equals(auction.getLot().getVendorDB().getUsername())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Offer");
            alert.setHeaderText("Error ");
            alert.setContentText("Il creatore dell'asta non puo' fare offerte sulla stessa");

            alert.showAndWait();
        }
        else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Offer Dialog");
            dialog.setHeaderText("Higher Offer:" + auction.getHigherOffer());
            dialog.setContentText("Your Offer:");

            Optional<String> input = dialog.showAndWait();
            input.ifPresent(offer -> {
                int offerInt;
                try {
                    offerInt = Integer.parseInt(offer);
                    if(client.makeBid(client.getLoggedUser(),offerInt,auction.getId())) {
                        auction = client.getAuction(auction.getId());
                        initializeNow();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Offer");
                        alert.setHeaderText("Error ");
                        alert.setContentText("L'offeta e' stata superata, ricarica");

                        alert.showAndWait();
                    }
                }catch (NumberFormatException e) {
                }catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public ClientManager getClient() {
        return client;
    }

    public void setClient(ClientManager client) {
        this.client = client;
        initializeNow();
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public void setPopUpStage(Stage popUpStage) {
        this.popUpStage = popUpStage;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
