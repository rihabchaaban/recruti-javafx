package controllers;

import models.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class listOfferFrontCardRecruteurController implements Initializable {

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
    void open_UpdateOffer(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/EditOfferFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Offer");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerOffer(ActionEvent event) throws SQLException {
        OfferService os = new OfferService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Offer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'offre sélectionnée
            int id = this.off.getId();

            // Supprimer l'offre de la base de données
            os.supprimer(id);
        }
    }
}
