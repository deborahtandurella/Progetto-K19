package View.Pages;

import Domain.AuctionMechanism.Auction;
import Domain.ClientManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionListController implements Initializable {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private ListView<Auction> auctionList;

    private final ObservableList<Auction> auction = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auction.clear();
    }





    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }
}
