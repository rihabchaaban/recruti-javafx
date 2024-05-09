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

public class gestionRessourceController  implements Initializable {


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
            gestionRessourcePane.getChildren().removeAll();
            gestionRessourcePane.getChildren().setAll(fxml);
        } else if(event.getSource() == btnViewRessource){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listRessource.fxml"));
            gestionRessourcePane.getChildren().removeAll();
            gestionRessourcePane.getChildren().setAll(fxml);
        }
    }
}
