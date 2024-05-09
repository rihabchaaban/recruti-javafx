package controllers;

import models.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.EventService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listEventController implements Initializable {

    @FXML
    private AnchorPane listEventPane;

    @FXML
    private HBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            EventService eventService = new EventService();
            List<Event> events = eventService.getAll();

            for(Event event : events) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemEvent.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    VBox vBox1 = (VBox) anchorPane.getChildren().get(0);
                    itemEventController itemController = fxmlLoader.getController();
                    itemController.setData(event);
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
