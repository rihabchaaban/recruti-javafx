package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button btnRess;

    @FXML
    private Button btnBiblio;

    @FXML
    private AnchorPane view_pages;

    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        if(event.getSource() == btnBiblio) {
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionBiblio.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }else if(event.getSource() == btnRess) {
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionRessource.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }
    }
}