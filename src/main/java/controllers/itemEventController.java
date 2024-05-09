package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Event;
import javafx.stage.Stage;
import services.EventService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemEventController {
    @FXML
    private Button btnModifierEvent;

    @FXML
    private Button btnParticiper;

    @FXML
    private Button btnSupprimerOffer;

    @FXML
    private AnchorPane itemEventPane;

    @FXML
    private Label labelContactEvent;

    @FXML
    private Label labelDateEvent;

    @FXML
    private Label labelDescriptionEvent;

    @FXML
    private Label labelHeureEvent;

    @FXML
    private Label labelLocalisationEvent;

    @FXML
    private Label labelThemeEvent;

    @FXML
    private Label labelTitreEvent;
    @FXML
    private ImageView labelimgEvent;



    public static int id;

    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    Event event;
    public Event selectedEvent;

    public void setData(Event event) {
        this.event=event;
        this.selectedEvent = event;

        labelTitreEvent.setText(event.getNom_e());
        labelDescriptionEvent.setText(event.getDescription());
        labelHeureEvent.setText(event.getHeure_e());
        labelLocalisationEvent.setText(event.getLieu_e());
        labelDateEvent.setText(event.getDate_e());
        labelThemeEvent.setText(event.getTheme_e());
        labelContactEvent.setText(event.getContact_e());
        labelimgEvent.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/" + event.getImage_e()));

        this.id=event.getId();
        System.out.println(this.id+"////////////////////////////////////////////////////////");
    }
    public  Event getSelectedEvent() {
        return selectedEvent;
    }
    public Event getData (Event event){
        this.event = event;
        return this.event;
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void open_Add_Participer(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/AddParticipation.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();

    }

    @FXML
    void open_UpdateEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditEvent.fxml"));
        Parent root = loader.load();
        EditEventController controller = loader.getController();
        controller.initData(selectedEvent); // Passer l'événement sélectionné au contrôleur d'édition
        Stage stage = new Stage();
        stage.setTitle("Modifier événement");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    @FXML
    void supprimerEvent(ActionEvent event) {
        EventService os = new EventService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet evenement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'offre sélectionnée
            Event e = this.event;


            // Supprimer l'offre de la base de données
            os.delete(e);
        }

    }
}
