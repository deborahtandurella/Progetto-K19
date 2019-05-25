package View.Pages;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class CreaAstaController {
    @FXML
    private JFXTextField itemName;

    @FXML
    private JFXTextField description;

    @FXML
    private JFXTextField basePrice;

    @FXML
    private JFXDatePicker closeDate;

    @FXML
    private JFXTimePicker closeTime;

    @FXML
    private JFXButton loadImage;

    @FXML
    private JFXListView listview;

    @FXML
    private JFXButton createAuction;

    public void loadImageAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            String extension = selectedFile.getAbsolutePath().replaceAll("^[^.]*.", "");  //Regex per ricavare l'estensione
            if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg")) {
                listview.getItems().add(selectedFile.getAbsolutePath());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error File");
                alert.setHeaderText("File is not valid! Only jpg and png are allowed");

                alert.showAndWait();
            }
        }
    }

    public void createAuctionAction(ActionEvent event) {
        //Richiesta al backend di creazione asta
    }

}
