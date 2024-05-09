package controllers;

import models.Participation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ParticipationService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listParticipationController implements Initializable {

    @FXML
    private AnchorPane listParticipationPane;

    @FXML
    private HBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ParticipationService participationService = new ParticipationService();
            List<Participation> participations = participationService.getAll();

            for(Participation participation : participations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemParticipation.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    VBox vBox1 = (VBox) anchorPane.getChildren().get(0);
                    itemParticipationController itemController = fxmlLoader.getController();
                    itemController.setData(participation);
                    vBox.getChildren().add(vBox1);
                } catch (IOException ex) {
                    Logger.getLogger(itemEventController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
