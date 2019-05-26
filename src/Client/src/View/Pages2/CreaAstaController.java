package View.Pages2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class CreaAstaController extends VBox {
    @FXML
    private TextField nameLot;

    @FXML
    private TextField description;

    @FXML
    private TextField category;

    @FXML
    private TextField startingPrice;

    @FXML
    private DatePicker openingDate;

    @FXML
    private DatePicker closingDate;

    @FXML
    private Button imageButton;

    @FXML
    private ImageView imageLot;

    @FXML
    private Button submitButton;

    public CreaAstaController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FormCreaAsta.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void addimage(ActionEvent event) {

    }

    @FXML
    void createAuction(ActionEvent event) {

    }

}
