package Client.Controller;

import Server.Domain.Auction;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class AuctionWinnerCardController extends TemplateController {
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
    void backToLoginScreen() {
        popUpStage.close();
    }

    @FXML
    public void handleCursorHand() {
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor() { popUpStage.getScene().setCursor(Cursor.DEFAULT); }

    public void initializeNow() throws RemoteException {
        //PROTECTED VARIATIONS

        auctionName.setText(auction.getDescriptionLot());
        higherOffer.setText("$" + auction.getLastBidAmount());
        vendor.setText(auction.getUsernameVendorDB());
        e_mail.setText(client.getVendorEmail(auction.getUsernameVendorDB()));
        ControllerServices.getInstance().setImagetoTheAuction(auction,auctionImage);
    }


    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }


}

