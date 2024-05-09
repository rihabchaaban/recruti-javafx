package controllers;

import models.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class listOfferFrontCardController implements Initializable {

    @FXML
    private Label labelDateOffre;

    @FXML
    private Label labelDureeOffre;

    @FXML
    private Label labelLocalisationOffre;

    @FXML
    private Label labelSalaireOffre;

    @FXML
    private Label labelTitreOffre;

    @FXML
    private Label labelTypeOffre;


    Offer off;
    private static int idOff;

    public int getIdOff(){
        return this.idOff;
    }
    public void setData (Offer off){
        this.off = off;
        labelTitreOffre.setText(off.getTitre_o());
        labelTypeOffre.setText(off.getType_o());
        labelDureeOffre.setText(off.getDure_o());
        labelSalaireOffre.setText(off.getSalaire_o());
        labelLocalisationOffre.setText(off.getLocalisation_o());
        labelDateOffre.setText(String.valueOf(off.getDate_o()));
        this.idOff=off.getId();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void openAddCondidature(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/addCondidatureFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Condidature");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }
}
