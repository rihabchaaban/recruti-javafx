package gui;

import entities.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemOfferController implements Initializable {

    @FXML
    private Button btnModifierOffer;

    @FXML
    private Button btnSupprimerOffer;

    @FXML
    private AnchorPane itemOfferPane;

    @FXML
    private Label labelDateOffer;

    @FXML
    private Label labelDescriptionOffer;

    @FXML
    private Label labelDureeOffer;

    @FXML
    private Label labelLocalisationOffer;

    @FXML
    private Label labelSalaireOffer;

    @FXML
    private Label labelTitreOffer;

    @FXML
    private Label labelTypeOffer;


    private static int id;

    public int getId(){
        return this.id;
    }

    Offer off;
    public void setData (Offer off){
        this.off = off;

        labelTitreOffer.setText(off.getTitre_o());
        //labelDescriptionOffer.setText(off.getDescription_o());
        labelTypeOffer.setText(String.valueOf(off.getType_o()));
        labelLocalisationOffer.setText(off.getLocalisation_o());
        labelDateOffer.setText(String.valueOf(off.getDate_o()));
        labelDureeOffer.setText(off.getDure_o());
        labelSalaireOffer.setText(off.getSalaire_o());
        this.id=off.getId();
    }

    public Offer getData (Offer off){
        this.off = off;
        return this.off;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateOffer(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("EditOffer.fxml"));
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

    @FXML
    void open_Add_Condidature(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddCondidature.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Condidature");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }
}
