package controllers;

import models.Event;
import models.Participation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.EventService;
import services.ParticipationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditParticipationFrontController implements Initializable {
    @FXML
    private AnchorPane addEventPane;

    @FXML
    private Button btnClearParticipation;

    @FXML
    private Button btnUpdateParticipation;

    @FXML
    private TextField nameP;

    @FXML
    private ComboBox<String> txtRole;

    @FXML
    private TextField txtStatut;

    @FXML
    private TextArea txtfeedback;

    @FXML
    private TextField txtnameevent;

    Participation participation;
    Event event;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> themes = FXCollections.observableArrayList(
                "Thème 1", "Thème 2", "Thème 3"); // Adapter les thèmes selon vos besoins
        txtRole.setItems(themes);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/cardParticipation.fxml"));

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox hBox = (VBox) anchorPane.getChildren().get(0);
            cardParticipationController item = fxmlLoader.getController();
            ParticipationService os = new ParticipationService();

            participation = os.getById(item.getId());
            nameP.setText(String.valueOf(participation.getNom_participant()));
            txtStatut.setText(participation.getStatut());
            txtfeedback.setText(participation.getFeedback());
            EventService es = new EventService();


            event = es.getById(participation.getEvent_id());
            txtnameevent.setText(event.getNom_e());


            txtRole.setValue(participation.getRole());

        } catch (IOException ex) {
            Logger.getLogger(cardParticipationController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierParticipation(ActionEvent event) {
        if (txtRole.getValue().isEmpty() || nameP.getText().isEmpty() || txtnameevent.getText().isEmpty()
                || txtfeedback.getText().isEmpty() || txtStatut.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Participation.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            updateParticipation();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Participation a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
        if(event.getSource() == btnClearParticipation){
            clearFieldsParticipation();
        }

    }

    @FXML
    void clearFieldsParticipation( ) {

        nameP.clear();
        txtStatut.clear();
        txtfeedback.clear();
        txtRole.setValue("Choisir le Role");

    }
    void updateParticipation(){
        // From Formulaire
        String nom = nameP.getText();
        String statut = txtStatut.getText();
        String role = txtRole.getValue();
        String feedback = txtfeedback.getText();




        Participation e = new Participation(participation.getId(), participation.getEvent_id(), role, statut, feedback, nom);
        ParticipationService os = new ParticipationService();
        try {
            os.update(e);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
