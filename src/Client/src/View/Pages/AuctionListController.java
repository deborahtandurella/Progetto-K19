package View.Pages;

import Domain.AuctionMechanism.Auction;
import Domain.AuctionMechanism.SimpleAuction;
import Domain.ClientManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionListController {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private ListView<SimpleAuction> auctionList;

    private final ObservableList<SimpleAuction> auction = FXCollections.observableArrayList();



    public void initializeList() {
        ArrayList<SimpleAuction> Alist = null;
        auction.clear();
        //Richiedo al server una lista di 10 aste al massimo, successivamente posso chiedere di prenderne altre 10
        try {
            Alist = client.requestListAuction();
        }catch (RemoteException e) {
            e.printStackTrace();
        }
        if(Alist != null) {
            for (int i = 0; i < Alist.size(); i++) {
                auction.add(Alist.get(i));
            }

            auctionList.setCellFactory(new Callback<ListView<SimpleAuction>, ListCell<SimpleAuction>>() {
                @Override
                public ListCell<SimpleAuction> call(ListView<SimpleAuction> param) {
                    ListCell<SimpleAuction> cell = new ListCell<SimpleAuction>() {
                        protected void updateItem(SimpleAuction au, boolean bt1) {
                            super.updateItem(au,bt1);
                            if(bt1)
                                setStyle("-fx-background-color: #81c784"); // Da togliere se si vuole lo stacco
                            if(au != null) {
                                //Inserire immagine
                                setText("Id:" + Integer.toString(au.getAuctionId()) + "\t\t" + "Name:" + au.getAuctionName() + "\t\t " + "Value:" + Integer.toString(au.getAmount()) + "\n\t\t" + "Close Date:" + au.getCloseDate().toString());
                                setStyle("-fx-background-color: #81c784"); //Da togliere se si vuole lo stacco
                            }
                        }
                    };
                    return cell;
                }
            });
            auctionList.setItems(auction);
        }
    }





    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) {
        this.client = client;
        initializeList();
    }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }
}
