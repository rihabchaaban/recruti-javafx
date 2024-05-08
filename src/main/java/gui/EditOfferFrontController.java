package gui;

import entities.Offer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditOfferFrontController implements Initializable {
    @FXML
    private Button btnClearOffer;

    @FXML
    private Button btnUpdateOffer;

    @FXML
    private DatePicker txtDateOffer;

    @FXML
    private TextArea txtDescriptionOffer;

    @FXML
    private TextField txtDureeOffer;

    @FXML
    private TextField txtLocalisationOffer;

    @FXML
    private TextField txtSalaireOffer;

    @FXML
    private TextField txtTitreOffer;

    @FXML
    private ComboBox<String> txtTypeOffer;

    @FXML
    private AnchorPane updateOfferPane;


    Offer off;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Job","Freelance","Stage");
        txtTypeOffer.setItems(options);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("listOfferFrontCardRecruteur.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox hBox = (VBox) anchorPane.getChildren().get(0);
            listOfferFrontCardRecruteurController item = fxmlLoader.getController();
            OfferService os = new OfferService();

            off = os.getById(item.getIdOff());
            txtTitreOffer.setText(String.valueOf(off.getTitre_o()));
            txtDescriptionOffer.setText(off.getDescription_o());
            txtTypeOffer.setValue(off.getType_o());
            txtLocalisationOffer.setText(off.getLocalisation_o());
            //txtDateOffer.setValue(off.getDate_o());
            txtDureeOffer.setText(off.getDure_o());
            txtSalaireOffer.setText(off.getSalaire_o());

        } catch (IOException ex) {
            Logger.getLogger(listOfferFrontCardRecruteurController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clearFieldsOffer() {
        txtDateOffer.getEditor().clear();
        txtDescriptionOffer.clear();
        txtDureeOffer.clear();
        txtTitreOffer.clear();
        txtSalaireOffer.clear();
        txtLocalisationOffer.clear();
        txtTypeOffer.setValue("Choisir le type");
    }

    @FXML
    void updateOffer(ActionEvent event) {
        if (txtTypeOffer.getValue().isEmpty() || txtLocalisationOffer.getText().isEmpty() || txtSalaireOffer.getText().isEmpty()
                || txtTitreOffer.getText().isEmpty() || txtDescriptionOffer.getText().isEmpty() || txtDureeOffer.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Offer.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifOffer();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Offer a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
    }


    void modifOffer(){
        // From Formulaire
        String titre = txtTitreOffer.getText();
        String description = txtDescriptionOffer.getText();
        String type = txtTypeOffer.getValue();
        String localisation = txtLocalisationOffer.getText();
        Date date=null;
        try {
            LocalDate localDate = txtDateOffer.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        String duree = txtDureeOffer.getText();
        String salaire = txtSalaireOffer.getText();
        int user_id=1;

        Offer o = new Offer(
                off.getId(), titre, description, type, localisation, date, duree, salaire, user_id);
        OfferService os = new OfferService();
        os.modifier(o);
    }
}
