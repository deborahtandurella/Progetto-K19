package View;

import Domain.ClientManager;
import Domain.ConnectionLayer;
import View.Pages.HomeController;
import View.Pages.LoginDataController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;
    private ConnectionLayer connection;
    private ClientManager c;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AuctionHouse");

        initRootLayout();

        //Event Handler per la chiusura del client dopo aver premuto sulla classica X
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                connection.setUserWantDisconnect(true);
            }
        });

    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            connection = new ConnectionLayer("hii");
            c = new ClientManager(connection, connection.getServer());

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("./Pages/Home.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //Passo riferimento a stage e connessione
            ((HomeController)loader.getController()).setClient(c);
            ((HomeController)loader.getController()).setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
