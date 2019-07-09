package Client.Controller;

import Client.Domain.ClientManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ResetPasswordController {

    @FXML
    private AnchorPane windowsPane;

    private ClientManager clientManager;
    private Stage primaryStage;

    private Stage popUpStage;

    @FXML
    private FontAwesomeIconView backButton;

    @FXML
    private JFXPasswordField passwordRepeated;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXPasswordField passwordNew;

    @FXML
    private JFXPasswordField passwordOld;

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
    void handleRegistrationPassword(ActionEvent event) {
        String oldpsw= passwordOld.getText();
        String newpsw = passwordNew.getText();
        String repeatedpsw= passwordRepeated.getText();
        //_-------------

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
