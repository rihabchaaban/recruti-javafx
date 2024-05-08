package conrollers;

import models.Event;
import models.Participation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.EventService;
import services.ParticipationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class cardParticipationController {

    @FXML
    private Button btnModifierP;

    @FXML
    private Button btnSupprimerP;

    @FXML
    private AnchorPane itemEventPane;

    @FXML
    private Label labelNameEvent;

    @FXML
    private Label labelRole;

    @FXML
    private Label labelStatutP;

    @FXML
    private Label labelfeedback;

    @FXML
    private Label labelnameP;


    public static int id;

    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    Participation participation;
    Event event;

    public void setData(Participation participation) {
        this.participation = participation;
        EventService es = new EventService();
        try {
            event = es.getById(participation.getEvent_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        labelRole.setText(participation.getRole());
        labelStatutP.setText(participation.getStatut());
        labelfeedback.setText(participation.getFeedback());
        labelnameP.setText(participation.getNom_participant());
        labelNameEvent.setText(event.getNom_e());

        this.id = participation.getId();
        System.out.println(this.id + "////////////////////////////////////////////////////////");
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }





    @FXML
    void open_UpdateEvent(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("EditParticipationFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();

    }

    @FXML
    void supprimerParticipation(ActionEvent event) {
        ParticipationService os = new ParticipationService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet Participation ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Participation p = this.participation;



            os.delete(p);
        }

    }

}
