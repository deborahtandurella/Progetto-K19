package Client.Controller;

import Client.Domain.ClientManager;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TitleController {
    private ClientManager client;
    private Stage primaryStage;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton myAuction;

    @FXML
    private JFXButton favoriteButton;

    @FXML
    private JFXButton userButton;

    private AuctionListController auctionListController;

    private UserPageController userPageController;

    private HomeController homeController;

    @FXML
    public void handleCursorHand(MouseEvent me) {
        primaryStage.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void handleCursor(MouseEvent me) {
        primaryStage.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void handleLogout(){
        try {
            if (client.logoutGUI()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
                AnchorPane root = loader.load();

                Scene scene = new Scene(root);
                // Show the scene containing the root layout.
                primaryStage.setScene(scene);

                ((LoginController) loader.getController()).setPrimaryStage(primaryStage);
                ((LoginController) loader.getController()).setClient(client);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Logout");
                alert.setHeaderText("Some error occured, contact your administrator");

                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewFavorites() {
        try {
            setVisibleButtons();

            auctionListController.loadFavorite();
            new BounceOut(favoriteButton).play();
            favoriteButton.setDisable(true);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewMyAuction () {
        try {
            setVisibleButtons();

            new BounceOut(myAuction).play();
            myAuction.setDisable(true);
            auctionListController.loadMyAuction();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void viewUser() throws IOException {
        try {
            setVisibleButtons();

            new BounceOut(userButton).play();
            userButton.setDisable(true);


        }catch (Exception e) {
            e.printStackTrace();
        }

        BoxBlur blur = new BoxBlur(5,5,5);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserPage.fxml"));
        Parent signup = (Parent) loader.load();

        Stage popUpStageSignUp = new Stage(StageStyle.TRANSPARENT);
        popUpStageSignUp.initOwner(primaryStage);
        popUpStageSignUp.initModality(Modality.APPLICATION_MODAL);
        Scene sigUpScene = new Scene(signup);
        popUpStageSignUp.setScene(sigUpScene);

        // Calculate the center position of the parent Stage
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        popUpStageSignUp.setOnShowing(ev -> popUpStageSignUp.hide());

        // Relocate the pop-up Stage
        popUpStageSignUp.setOnShown(ev -> {
            popUpStageSignUp.setX(centerXPosition - popUpStageSignUp.getWidth()/2d);
            popUpStageSignUp.setY(centerYPosition - popUpStageSignUp.getHeight()/2d);
            popUpStageSignUp.show();
        });

        popUpStageSignUp.show();

        primaryStage.getScene().lookup("#windowsPane").setEffect(blur);

        new FadeIn(signup).play();


        userPageController = (UserPageController) loader.getController();
        userPageController.setPrimaryStage(primaryStage);
        userPageController.setClient(client);
        userPageController.setPopUpStage(popUpStageSignUp);
        userPageController.initializeWindow();
        userPageController.initializeNow();

    }

    public void setVisibleButtons() {
        new BounceIn(favoriteButton).play();
        favoriteButton.setDisable(false);

        new BounceIn(userButton).play();
        userButton.setDisable(false);

        new BounceIn(myAuction).play();
        myAuction.setDisable(false);
    }


    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public AuctionListController getAuctionListController() {
        return auctionListController;
    }

    public void setAuctionListController(AuctionListController auctionListController) { this.auctionListController = auctionListController; }
    

    public JFXButton getMyAuction() { return myAuction; }

    public JFXButton getFavoriteButton() { return favoriteButton; }

    public HomeController getHomeController() { return homeController; }

    public void setHomeController(HomeController homeController) { this.homeController = homeController; }
}
