package Client.Controller;

import Client.Domain.ClientManager;
import animatefx.animation.FadeIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class TemplateController {
    ClientManager client;
    Stage primaryStage;
    Stage popUpStage;

    FXMLLoader loader = null;
    private Parent parent = null;

    Stage loadScenePopUp(String viewPath) throws IOException {
        // Load root layout from fxml file.
        loader = new FXMLLoader(getClass().getResource(viewPath));
        parent = (Parent) loader.load();

        Stage popUp = new Stage(StageStyle.TRANSPARENT);
        popUp.initOwner(primaryStage);
        popUp.initModality(Modality.APPLICATION_MODAL);
        Scene sigUpScene = new Scene(parent);
        popUp.setScene(sigUpScene);

        // Calculate the center position of the parent Stage
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        popUp.setOnShowing(ev -> popUp.hide());

        // Relocate the pop-up Stage
        popUp.setOnShown(ev -> {
            popUp.setX(centerXPosition - popUp.getWidth()/2d);
            popUp.setY(centerYPosition - popUp.getHeight()/2d);
            popUp.show();
        });

        popUp.show();

        new FadeIn(parent).play();

        return popUp;
    }


    public ClientManager getClient() { return client; }

    public void setClient(ClientManager client) { this.client = client; }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public Stage getPopUpStage() { return popUpStage; }

    public void setPopUpStage(Stage popUpStage) { this.popUpStage = popUpStage; }
}
