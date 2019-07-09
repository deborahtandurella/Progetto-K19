package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class AuctionWinnerCardController {
    private ClientManager client;
    private Stage popUpStage;
    private Auction auction;

    @FXML
    private AnchorPane windowsPane;

    @FXML
    private ImageView auctionImage;

    @FXML
    private Label auctionName;

    @FXML
    private Label vendor;

    @FXML
    private Label higherOffer;

    @FXML
    private Hyperlink e_mail;

    @FXML
    void backToLoginScreen(MouseEvent event) {
        popUpStage.close();
    }
    public void initializeNow(){
        //PROTECTED VARIATIONS
        auctionName.setText(auction.getLot().getDescription());
        higherOffer.setText("$" + auction.getLastBid().getAmount());
        vendor.setText(auction.getUsernameVendorDB());
        e_mail.setText("e-mail");// aggiungiamo e-mail
        Image img;


        if(auction.getImage() == null) {

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
                img = new Image(new FileInputStream(absolutePath),268,226,false,false);

                auctionImage.setImage(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                img = new Image(new FileInputStream(auction.getImage()),268,226,false,false);

                auctionImage.setImage(img);
            }catch (IOException e) {
                e.printStackTrace();
            }
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

