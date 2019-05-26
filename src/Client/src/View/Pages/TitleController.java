package View.Pages;

import Domain.ClientManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TitleController {
    private ClientManager clientManager;
    private Stage primaryStage;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton userSection;

    @FXML
    private JFXButton myAuction;

    @FXML
    private JFXButton inventory;

    private void handleLogout() {

    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
