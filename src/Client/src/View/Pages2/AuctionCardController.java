package View.Pages2;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.Lot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class AuctionCardController  implements Initializable{
    @FXML
    private Label auctionName;
    @FXML
    private Label startingOffer;
    @FXML
    private ImageView auctionImage;
    @FXML
    private Label timer;

    private Auction auction;


    public AuctionCardController() {
        Lot a= new Lot("Palla da bowling", 10);
        System.out.println(a);
        this.auction = new Auction(a,LocalDateTime.of(2019,05,24,11,00));
        auctionName=new Label();
        startingOffer= new Label();
        auctionImage=new ImageView();
        timer=new Label();
    }
    //Il metodo initialize è quello che setta i valori, non so perchè ma è così
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionName.setText(auction.getLot().getDescription());
        startingOffer.setText("$"+auction.getLot().getBasePrice());
        BufferedImage bufferedImage;
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000000),
                ae ->{} ));
        timeline.play();
        timer.setText(timeline.getCurrentTime()+"");
        try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\diret\\Documents\\Progetto-K19\\src\\Client\\src\\View\\Images\\star-empty-128.png"));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            this.auctionImage.setImage(image);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }


    }


}
