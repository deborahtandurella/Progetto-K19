package View;

import Domain.ClientManager;
import Domain.ConnectionLayer;
import View.Pages.LoginDataController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
            loader.setLocation(getClass().getResource("./Pages/Login.fxml"));
            rootLayout = (AnchorPane) loader.load();

            ((LoginDataController)loader.getController()).setClient(c);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
