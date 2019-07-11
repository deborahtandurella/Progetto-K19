package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ControllerServices {

    private static ControllerServices instance;
    private ClientManager client;

    public static ControllerServices getInstance() {
        if(instance==null)
            instance = new ControllerServices();
        return instance;
    }

    public void setImagetoTheAuction(Auction auction, ImageView auctionImage){

        if(auction.getImage() == null) {
            loadLocalImage(auctionImage);
        }
        else {
            try {
                Image img = new Image(new FileInputStream(auction.getImage()),268,226,false,false);
                auctionImage.setImage(img);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadLocalImage(ImageView auctionImage){
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
            Image img = new Image(new FileInputStream(absolutePath),268,226,false,false);
            auctionImage.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCenterofPage(Stage children,Stage parent){
        double centerXPosition =parent.getX() + parent.getWidth()/2d;
        double centerYPosition = parent.getY() + parent.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        children.setOnShowing(ev ->children.hide());

        // Relocate the pop-up Stage
        children.setOnShown(ev -> {
            children.setX(centerXPosition - children.getWidth()/2d);
            children.setY(centerYPosition - children.getHeight()/2d);
            children.show();
        });

        children.show();
    }
}
