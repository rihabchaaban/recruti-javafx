package controllers;

import models.Condidature;
import models.Offer;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.CondidatureService;
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemCondidatureController implements Initializable {

    @FXML
    private Button btnModifierCond;

    @FXML
    private Button btnSupprimerCond;

    @FXML
    private AnchorPane itemCondidaturePane;

    @FXML
    private ImageView labelCvCond;

    @FXML
    private Label labelEmailCond;

    @FXML
    private Label labelNomCond;

    @FXML
    private Label labelOffreCond;

    private static int id;

    public int getId(){
        return this.id;
    }

    Condidature cond;
    public void setData (Condidature cond) {
        this.cond = cond;
        OfferService os = new OfferService();

        labelNomCond.setText(cond.getNom_c());
        labelEmailCond.setText(String.valueOf(cond.getEmail_c()));

        labelCvCond.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/"+cond.getCv_c()));
        try {
            labelOffreCond.setText(String.valueOf(os.getById(cond.getOffer_id()).getTitre_o()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.id=cond.getId();
    }

    public Condidature getData (Condidature cond){
        this.cond = cond;
        return this.cond;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateCondidature(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/EditCondidature.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Condidature");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerCondidature(ActionEvent event) throws SQLException {
        CondidatureService cs = new CondidatureService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette Condidature ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la condidature sélectionnée
            int id = this.cond.getId();

            // Supprimer la condidature de la base de données
            cs.supprimer(id);
        }
    }
}
