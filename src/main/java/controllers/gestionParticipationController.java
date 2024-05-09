package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionParticipationController implements Initializable {


    @FXML
    private Button btnAddEvent;

    @FXML
    private Button btnViewEvent;

    @FXML
    private AnchorPane gestionOfferPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnAddEvent){
            Parent fxml = FXMLLoader.load(getClass().getResource("/addParticipation.fxml"));
            gestionOfferPane.getChildren().removeAll();
            gestionOfferPane.getChildren().setAll(fxml);
        } else if(event.getSource() == btnViewEvent){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listParticipation.fxml"));
            gestionOfferPane.getChildren().removeAll();
            gestionOfferPane.getChildren().setAll(fxml);
        }
    }
}
