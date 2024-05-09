package controllers;

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
import models.Ressource;
import services.BiblioService;
import services.RessourceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemRessourceController implements Initializable {

    @FXML
    private Button btnModifierRess;

    @FXML
    private Button btnSupprimerRess;

    @FXML
    private AnchorPane itemRessourcePane;

    @FXML
    private ImageView imageViewRessource;

    @FXML
    private Label labelFieldRess;

    @FXML
    private Label labelTitreRess;

    @FXML
    private Label labelBiblioRess;

    private static int id;

    public int getId(){
        return this.id;
    }

    Ressource ress ;
    public void setData (Ressource ress) throws SQLException {
        this.ress = ress;
        BiblioService bs = new BiblioService();

        labelTitreRess.setText(ress.getTitre_b());
        labelFieldRess.setText(String.valueOf(ress.getCategorie_resso_b()));
        imageViewRessource.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/"+ress.getImage_b_ressource()));
        labelBiblioRess.setText(String.valueOf(bs.getById(ress.getBiblio_id()).getNom_b()));
        this.id=ress.getId();
    }

    public Ressource getData (Ressource ress ){
        this.ress = ress;
        return this.ress;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateRessource(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/EditRessourcee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Ressource");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerRessource(ActionEvent event) throws SQLException {
        RessourceService rs = new RessourceService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to delete this Resource?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la condidature sélectionnée
            int id = this.ress.getId();

            // Supprimer la condidature de la base de données
            rs.supprimer(id);
        }
    }
}
