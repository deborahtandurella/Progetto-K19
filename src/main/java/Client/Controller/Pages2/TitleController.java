package Client.Controller.Pages2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TitleController extends VBox {

    @FXML
    private Button inventoryButton;

    @FXML
    private Button userButton;

    @FXML
    private Button myAuctionsButton;

    @FXML
    private Button logoutButton;

    public TitleController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Title.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void showInventory(ActionEvent event) {

    }

    @FXML
    void showMyAuctions(ActionEvent event) {

    }

    @FXML
    void showMyProfile(ActionEvent event) {

    }

}
