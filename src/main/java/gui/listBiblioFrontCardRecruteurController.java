package gui;

import entities.Biblio;
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
import services.BiblioService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class listBiblioFrontCardRecruteurController implements Initializable {

    @FXML
    private Label labelDateBiblio;

    @FXML
    private Label labelNomBiblio;

    @FXML
    private Label labelDomaineBiblio;


    Biblio biblio;
    private static int idBiblio;

    public int getIdBiblio(){
        return this.idBiblio;
    }
    public void setData (Biblio biblio){
        this.biblio = biblio;
        labelNomBiblio.setText(biblio.getNom_b());
        labelDomaineBiblio.setText(biblio.getDomaine_b());
        labelDateBiblio.setText(String.valueOf(biblio.getDate_creation_b()));
        this.idBiblio=biblio.getId();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateBiblio(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("EditBiblioFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Library");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerBiblio(ActionEvent event) throws SQLException {
        BiblioService bs = new BiblioService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to delete this library?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de biblio sélectionnée
            int id = this.biblio.getId();

            // Supprimer biblio de la base de données
            bs.supprimer(id);
        }
    }
}
