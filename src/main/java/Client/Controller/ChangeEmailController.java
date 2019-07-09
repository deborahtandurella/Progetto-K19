package Client.Controller;

import Client.Domain.ClientManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeEmailController {

    @FXML
    private AnchorPane windowsPane;

    @FXML
    private FontAwesomeIconView backButton;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField newEmail;

    private ClientManager clientManager;
    private Stage primaryStage;

    private Stage popUpStage;

    @FXML
    void backToLoginScreen(MouseEvent event) {
        AnchorPane pane = (AnchorPane) primaryStage.getScene().lookup("#windowsPane");
        pane.setEffect(null);
        popUpStage.close();
    }

    @FXML
    void handleCursor(MouseEvent event) {

        popUpStage.getScene().setCursor(Cursor.DEFAULT);
        backButton.setStyle("-fx-background-color:transparent;");
    }

    @FXML
    void handleCursorHand(MouseEvent event) {

        backButton.setStyle("-fx-background-color:#dae7f3;");
        popUpStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    void handleRegistrationEmail(ActionEvent event) {
        String email= newEmail.getText();
        String pass = password.getText();
        //.-.-.-..-.-.-.-..-
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

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public void setPopUpStage(Stage popUpStage) {
        this.popUpStage = popUpStage;
    }
}