package gui;

import entities.Event;
import entities.Participation;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addParticipationController implements Initializable {

    @FXML
    private AnchorPane addEventPane;

    @FXML
    private Button btnAddParticipation;

    @FXML
    private Button btnClearParticipation;

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

    Event event;
    @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            ObservableList<String> options = FXCollections.observableArrayList(
                    "info","Genie civil","Electro");
            txtRole.setItems(options);
            try {


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("itemEvent.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        VBox hBox = (VBox) anchorPane.getChildren().get(0);
        itemEventController item = fxmlLoader.getController();
        EventService os = new EventService();


        event = os.getById(item.getId());
        txtnameevent.setText(event.getNom_e());

    }catch (IOException ex) {
                Logger.getLogger(itemEventController.class.getName()).log(Level.SEVERE, null, ex);
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    @FXML
    void clearFieldsParticipation(ActionEvent event) {

    }
    private final ParticipationService participationService = new ParticipationService();


    @FXML
    void ajouterParticipation(ActionEvent event) throws IOException {

        if (!validateInputs())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre participation.");
            Optional<ButtonType> option = alert.showAndWait();
        } else {
            Participation participationToAdd = new Participation();

            String name = nameP.getText();
            String statut = txtStatut.getText();
            String feedback = txtfeedback.getText();
            String role = String.valueOf(txtRole.getValue());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemEvent.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            VBox hBox = (VBox) anchorPane.getChildren().get(0);
            itemEventController item = fxmlLoader.getController();
            EventService os = new EventService();



            Participation p = new Participation( item.getId(),name, statut, feedback, role);
            ParticipationService ps = new ParticipationService();

                ps.add(p);



            // Ajouter l'événement à la base de données


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajouté avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Participation a été ajouté avec succès.");
            Optional<ButtonType> option = alert.showAndWait();

            // Effacer les champs après l'ajout
            clearFieldsParticipation();
        }
    }

    private boolean validateInputs() {
        String nomParticipant = nameP.getText();
        String role = txtRole.getValue();
        String statut = txtStatut.getText();
        String feedback = txtfeedback.getText();

        // Valider les champs
        if (nomParticipant.isEmpty() || role == null || statut.isEmpty() || feedback.isEmpty()) {
            showAlert("Information manquante", "Vous devez remplir tous les détails concernant la participation.");
            return false;
        }

        // Ajoutez ici d'autres validations selon vos besoins

        return true; // Toutes les validations réussies
    }
    @FXML
    void clearFieldsParticipation() {
        nameP.clear();
        txtRole.getSelectionModel().clearSelection();
        txtStatut.clear();
        txtfeedback.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}




