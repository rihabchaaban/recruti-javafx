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

public class gestionCondidatureController implements Initializable {

    @FXML
    private Button btnViewCondi;

    @FXML
    private AnchorPane gestionCondidaturePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnViewCondi){
            Parent fxml = FXMLLoader.load(getClass().getResource("listCondidature.fxml"));
            gestionCondidaturePane.getChildren().removeAll();
            gestionCondidaturePane.getChildren().setAll(fxml);
        }
    }
}
