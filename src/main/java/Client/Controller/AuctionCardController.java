package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import Server.People.User;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class AuctionCardController extends TemplateController{
    private Auction auction;

    @FXML
    private AnchorPane windowsPane;


    private CreateAuctionFormController auctionFormController;

    private long day;
    private long hour;
    private long minute;
    private long second;

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
    private FontAwesomeIconView star;

    @FXML
    private JFXButton modifyAuctionButton;


    @FXML
    private Label timer;
    private Timeline timeline;


    public void initializeNow() {
        try {

            if(client.getLoggedUser().equals(auction.getLot().getVendorDB().getUsername()) && !client.isClosed(auction.getId())) {
                offerButton.setDisable(true);
                offerButton.setVisible(false);
            }
            else if(client.isClosed((auction.getId()))) {
                modifyAuctionButton.setDisable(true);
                modifyAuctionButton.setVisible(false);
                offerButton.setDisable(true);
                offerButton.setVisible(false);
            }
            else {
                modifyAuctionButton.setDisable(true);
                modifyAuctionButton.setVisible(false);
            }
            if(!client.userLikeAuction(auction.getId())) { //Se l'asta non e' tra le preferite
                star.setIcon(FontAwesomeIcon.STAR_ALT);

            }
            else {
                star.setIcon(FontAwesomeIcon.STAR);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //protected variation
        auctionName.setText(auction.getDescriptionLot());
        if(auction.getLastBid() != null) {
            higherOffer.setText("$" + auction.getLastBidAmount());
            bidderHigher.setText(auction.getLastActor());
        }
        else {
            higherOffer.setText("$"+auction.getHigherOffer());
            bidderHigher.setText("null");
        }

        vendor.setText(auction.getUsernameVendorDB());
        closeDate.setText(parseDate(auction.getClosingDate()));
        //

        if(auction.getImage() == null) {
            ControllerServices.getInstance().loadLocalImage(auctionImage);
        }
        else {
            try {
                Image img = new Image(new FileInputStream(auction.getImage()),268,226,false,false);
                auctionImage.setImage(img);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(auction.getClosingDate().isAfter(LocalDateTime.now())) { //Andrebbe richiesta data attuale al server
            ZonedDateTime zdt = auction.getClosingDate().atZone(ZoneId.of("Europe/Rome"));
            long millis = zdt.toInstant().toEpochMilli() - System.currentTimeMillis();
            countMilliToDay(millis);

            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (second > 0) {
                        second--;
                    } else if (second <= 0) {
                        second = 59;
                        if (minute > 0) {
                            minute--;
                        } else if (minute <= 0) {
                            minute = 59;
                            if (hour > 0) {
                                hour--;
                            } else if (hour <= 0) {
                                hour = 23;
                                if (day > 0) {
                                    day--;
                                }
                            }
                        }
                    }

                    timer.setText("D " + day + "  H " + hour + "  M " + minute + "  S " + second);

                    if (second <= 0 && minute <= 0 && hour <= 0 && day <= 0) {
                        timeline.stop();
                    }

                }
            }));
            timeline.play();
        }
        else {
            timer.setText("D " + 0 + "  H " + 0 + "  M " + 0 + "  S " + 0);
        }

    }

    @FXML
    public void modifyAuction() throws IOException {
        BoxBlur blur = new BoxBlur(3,3,3);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAuctionForm.fxml"));
        Parent root = (Parent) loader.load();

        Stage modifyStage = new Stage(StageStyle.TRANSPARENT);
        modifyStage.initOwner(popUpStage);
        modifyStage.initModality(Modality.APPLICATION_MODAL);
        modifyStage.setScene(new Scene(root));

        // Calculate the center position of the parent Stage
        ControllerServices.getInstance().setCenterofPage(modifyStage,popUpStage);
        windowsPane.setEffect(blur);



        //Animation
        new FadeIn(root).play();


        auctionFormController = (CreateAuctionFormController) loader.getController();

        auctionFormController.setClient(client);
        auctionFormController.setPopUpStage(modifyStage);
        auctionFormController.setPrimaryStage(popUpStage);
        auctionFormController.disableCreateAuction();
        auctionFormController.setAuction(auction);
        auctionFormController.setParameter();

        windowsPane.setEffect(blur);

    }

    public String parseDate(LocalDateTime closingTime) {

        return closingTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @FXML
    private void backToLoginScreen() {
        popUpStage.close();
    }

    @FXML
    private void addRemoveToFavourite() throws RemoteException {
        User user = client.getUser();
        Auction au = client.getAuction(auction.getId());
        try {
            if(client.userLikeAuction(au.getId())) { //Se contiene l'asta
                client.saveUserStateFavorites(user,au,0); //Togli l'asta
                star.setIcon(FontAwesomeIcon.STAR_ALT);
            }
            else {
                client.saveUserStateFavorites(user,au,1);
                star.setIcon(FontAwesomeIcon.STAR);
            }

        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void makeAnOffer() throws RemoteException {
        if(!client.isClosed(auction.getId())) {
            //protected variATIONS
            if (client.getLoggedUser().equals(auction.getUsernameVendorDB())) {//come sopra
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Offer");
                alert.setHeaderText("Error ");
                alert.setContentText("Il creatore dell'asta non puo' fare offerte sulla stessa");
                alert.initOwner(popUpStage);

                alert.showAndWait();
            } else {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Offer Dialog");
                dialog.setHeaderText("Higher Offer:" + auction.getHigherOffer());
                dialog.setContentText("Your Offer:");

                Optional<String> input = dialog.showAndWait();
                input.ifPresent(offer -> {
                    int offerInt;
                    try {
                        offerInt = Integer.parseInt(offer);
                        if (client.makeBid(client.getLoggedUser(), offerInt, auction.getId())) {
                            auction = client.getAuction(auction.getId());
                            initializeNow();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Offer");
                            alert.setHeaderText("Error ");
                            alert.setContentText("L'offerta e' stata superata, ricarica");

                            alert.showAndWait();
                        }
                    } catch (NumberFormatException e) {
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void initializeWindow() {
        popUpStage.getScene().setFill(Color.TRANSPARENT);
        windowsPane.setStyle(

                "-fx-background-insets: 5; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);"
        );
    }

    @FXML
    public void handleCursorHand(MouseEvent me) {
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor(MouseEvent me) {
        popUpStage.getScene().setCursor(Cursor.DEFAULT);
    }


    public void countMilliToDay(Long ms) {
        final int SECOND = 1000;
        final int MINUTE = 60 * SECOND;
        final int HOUR = 60 * MINUTE;
        final int DAY = 24 * HOUR;



        if (ms > DAY) {
            day = ms / DAY;
            ms %= DAY;
        }
        if (ms > HOUR) {
            hour = ms / HOUR;
            ms %= HOUR;
        }
        if (ms > MINUTE) {
            minute = ms / MINUTE;
            ms %= MINUTE;
        }
        if (ms > SECOND) {
           second = ms / SECOND;
           ms %= SECOND;
        }
    }


    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public CreateAuctionFormController getAuctionFormController() {
        return auctionFormController;
    }

    public void setAuctionFormController(CreateAuctionFormController auctionFormController) {
        this.auctionFormController = auctionFormController;
    }
}
