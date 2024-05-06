package gui;

import entities.Biblio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class listBiblioFrontCardController implements Initializable {

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
    void openShowRessource(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("listRessourceFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Show Resources");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }
}
