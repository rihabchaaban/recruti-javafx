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

public class gestionOfferController implements Initializable {
    @FXML
    private Button btnAddOffer;

    @FXML
    private Button btnViewOffer;

    @FXML
    private AnchorPane gestionOfferPane;
    @FXML
    private Button btnViewCondi;

    @FXML
    private AnchorPane gestionCondidaturePane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnAddOffer){
            Parent fxml = FXMLLoader.load(getClass().getResource("/addOffer.fxml"));
            gestionOfferPane.getChildren().removeAll();
            gestionOfferPane.getChildren().setAll(fxml);
        } else if(event.getSource() == btnViewOffer){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listOffer.fxml"));
            gestionOfferPane.getChildren().removeAll();
            gestionOfferPane.getChildren().setAll(fxml);
        }
        if(event.getSource() == btnViewCondi){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listCondidature.fxml"));
            gestionOfferPane.getChildren().removeAll();
            gestionOfferPane.getChildren().setAll(fxml);
        }
    }
}
