package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionBiblioController implements Initializable {
    @FXML
    private Button btnAddBiblio;

    @FXML
    private Button btnViewBiblio;

    @FXML
    private Button btnStatBiblio;

    @FXML
    private AnchorPane gestionBiblioPane;
    @FXML
    private Button btnAddRessource;

    @FXML
    private Button btnViewRessource;

    @FXML
    private AnchorPane gestionRessourcePane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnAddRessource){
            Parent fxml = FXMLLoader.load(getClass().getResource("/addRessource.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        } else if(event.getSource() == btnViewRessource){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listRessource.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        }
        if (event.getSource() == btnAddBiblio) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/addBiblio.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        } else if (event.getSource() == btnViewBiblio) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/listBiblio.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        } else if (event.getSource() == btnStatBiblio) {
            // Load statistics page
            Parent fxml = FXMLLoader.load(getClass().getResource("/statistics.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        }
    }
    }


