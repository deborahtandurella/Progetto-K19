package Client.Controller;

import Client.Domain.ClientManager;
import Server.Domain.Auction;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SearchController {
    private FXMLLoader fxml = null;
    private ClientManager client;



    @FXML
    private Button Searchbutton;

    @FXML
    private AnchorPane listView;

    @FXML
    private JFXTextField searchBar;
    @FXML
    private ListView<Auction> auctionList;


    public void searchMethod() {
        fxml = null;
        Parent root = null;

        String searched = searchBar.getText();
        ArrayList<Auction> Alist = new ArrayList<>();
        ObservableList<Auction> auction = FXCollections.observableArrayList();
        Alist = null;
        auction.clear();
        //Richiedo al server una lista di 10 aste al massimo, successivamente posso chiedere di prenderne altre 10
        try {
            Alist = client.requestListAuction();
        }catch (RemoteException e) {
            e.printStackTrace();
        }
        if(Alist != null) {
            for (int i = 0; i < Alist.size(); i++) {
                if(searchcontrol(Alist.get(i),searched))
                    auction.add(Alist.get(i));
            }
            auctionList =new ListView<>();
            auctionList.setCellFactory(new Callback<ListView<Auction>, ListCell<Auction>>() {
                @Override
                public ListCell<Auction> call(ListView<Auction> param) {
                    ListCell<Auction> cell = new ListCell<Auction>() {
                        Image img;
                        ImageView imgview = null;

                        protected void updateItem(Auction au, boolean bt1) {
                            super.updateItem(au,bt1);
                            if(bt1)
                                setStyle("-fx-background-color: #81c784"); // Da togliere se si vuole lo stacco
                            if(au != null) {
                                if(au.getImage().exists()) {
                                    try {
                                        img = new Image(new FileInputStream(au.getImage()),100,100,false,false);

                                        imgview = new ImageView();
                                        imgview.setImage(img);
                                    }catch (IOException e) {
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

                                        imgview = new ImageView();
                                        imgview.setImage(img);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                                setGraphic(imgview);
                                setText("Id:" + Integer.toString(au.getId()) + "\t\t" + "Name:" + au.getLot().getDescription() + "\t\t " + "Value:" + Integer.toString(au.getHigherOffer()) + "\n\t\t" + "Close Date:" + au.getClosingDate().toString());

                                setStyle("-fx-background-color: #81c784"); //Da togliere se si vuole lo stacco
                            }
                        }
                    };
                    return cell;
                }
            });
            auctionList.setItems(auction);
            listView.getChildren().setAll(auctionList);
        }
    }

    public void setClient(ClientManager client) {
        this.client = client;
    }

    private boolean searchcontrol(Auction a, String s){
        boolean succ=false;
        for (String word : s.split(" ")) {
            System.out.println(a.auctionInformation()+" "+ word +" "+ a.auctionInformation().contains(word));
            if (a.auctionInformation().contains(word))
                succ = true;
        }
        return succ;
    }

    }



