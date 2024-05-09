package controllers;

import models.Offer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.OfferService;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class addOfferFrontController implements Initializable {

    @FXML
    private AnchorPane addOffrePane;

    @FXML
    private Button btnAddOffre;

    @FXML
    private Button btnClearOffre;

    @FXML
    private DatePicker txtDateOffre;

    @FXML
    private TextArea txtDescriptionOffre;

    @FXML
    private TextField txtDureeOffre;

    @FXML
    private TextField txtLocalisationOffre;

    @FXML
    private TextField txtSalaireOffre;

    @FXML
    private TextField txtTitreOffre;

    @FXML
    private ComboBox<String> txtTypeOffre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Job","Freelance","Stage");
        txtTypeOffre.setItems(options);
    }

    @FXML
    void ajouterOffre(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddOffre){
            if (txtTypeOffre.getValue().isEmpty() || txtLocalisationOffre.getText().isEmpty() || txtSalaireOffre.getText().isEmpty()
                    || txtTitreOffre.getText().isEmpty() || txtDescriptionOffre.getText().isEmpty() || txtDureeOffre.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Offre.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                addOffer();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Offre a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();

                clearFieldsOffre();
            }
        }
        if(event.getSource() == btnClearOffre){
            clearFieldsOffre();
        }
    }

    @FXML
    void clearFieldsOffre() {
        txtDateOffre.getEditor().clear();
        txtDescriptionOffre.clear();
        txtDureeOffre.clear();
        txtTitreOffre.clear();
        txtSalaireOffre.clear();
        txtLocalisationOffre.clear();
        txtTypeOffre.setValue("Choisir le type");
    }

    private void addOffer() {

        // From Formulaire
        String titre = txtTitreOffre.getText();
        String description = txtDescriptionOffre.getText();
        String type = txtTypeOffre.getValue();
        String localisation = txtLocalisationOffre.getText();
        Date date=null;
        try {
            LocalDate localDate = txtDateOffre.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        String duree = txtDureeOffre.getText();
        String salaire = txtSalaireOffre.getText();
        int user_id=1;

        Offer o = new Offer(
                titre, description, type, localisation, date, duree, salaire, user_id);
        OfferService os = new OfferService();
        os.ajouter(o);
    }

}
