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
import models.Biblio;
import services.BiblioService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class itemBiblioController implements Initializable{
    @FXML
    private AnchorPane listBiblioPane;

    @FXML
    private Button btnModifierBiblio;

    @FXML
    private Button btnSupprimerBiblio;

    @FXML
    private Button btnAddRessource;

    @FXML
    private AnchorPane itemBiblioPane;

    @FXML
    private Label labelDateBiblio;

    @FXML
    private Label labelNameBiblio;

    @FXML
    private Label labelCategorieBiblio;

    @FXML
    private ImageView labelImageBiblio;

    private static int id;
    public int getId(){
        return this.id;
    }
    Biblio bib;
    @FXML
    void setData(Biblio bib) {
        this.bib = bib;
        labelNameBiblio.setText(bib.getNom_b());
        labelCategorieBiblio.setText(bib.getDomaine_b());
        labelDateBiblio.setText(String.valueOf(bib.getDate_creation_b()));
        labelImageBiblio.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/"+bib.getImage_b()));

        this.id = bib.getId();
    }
    public Biblio getData (Biblio bib){
        this.bib = bib;
        return this.bib;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void openUpdateBiblio(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/EditBiblio.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Biblio");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void deleteBiblio(ActionEvent event) throws SQLException {
        BiblioService biblioService = new BiblioService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to delete this library?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de biblio sélectionnée
            int id = this.bib.getId();
            biblioService.supprimer(id);
        }
    }
    @FXML
    void open_Add_Ressource(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/AddRessource.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Resource");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();

    }

}
