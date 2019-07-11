package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public abstract class TemplateController {
    ClientManager client;
    Stage primaryStage;
    Stage popUpStage;

    FXMLLoader loader = null;
    private Parent parent = null;

    Stage loadScenePopUp(String viewPath) throws IOException {
        // Load root layout from fxml file.
        loader = new FXMLLoader(getClass().getResource(viewPath));
        parent = (Parent) loader.load();

        Stage popUp = new Stage(StageStyle.TRANSPARENT);
        popUp.initOwner(primaryStage);
        popUp.initModality(Modality.APPLICATION_MODAL);
        Scene sigUpScene = new Scene(parent);
        popUp.setScene(sigUpScene);

        // Calculate the center position of the parent Stage
        ControllerServices.getInstance().setCenterofPage(popUp,primaryStage);

        new FadeIn(parent).play();

        return popUp;
    }


    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public Stage getPopUpStage() { return popUpStage; }

    public void setPopUpStage(Stage popUpStage) { this.popUpStage = popUpStage; }

    public void setImagetoAuction(Auction auction, ImageView auctionImage){
        Image img;
        if(auction.getImage() == null) {
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

}
