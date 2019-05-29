package Client.Controller.Pages2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends VBox implements Initializable {
    @FXML
    private AnchorPane headerPanel;

    @FXML
    private AnchorPane contentPanel;

    @FXML
    private AnchorPane searchPanel;

    @FXML
    private AnchorPane sectionCreateAuction;

    @FXML
    private AnchorPane sectionAuctionInEvidence;

    public HomeController() {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}