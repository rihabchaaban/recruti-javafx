package gui;
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
    private AnchorPane gestionBiblioPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnAddBiblio){
            Parent fxml = FXMLLoader.load(getClass().getResource("addBiblio.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        } else if(event.getSource() == btnViewBiblio){
            Parent fxml = FXMLLoader.load(getClass().getResource("listBiblio.fxml"));
            gestionBiblioPane.getChildren().removeAll();
            gestionBiblioPane.getChildren().setAll(fxml);
        }
    }
}
