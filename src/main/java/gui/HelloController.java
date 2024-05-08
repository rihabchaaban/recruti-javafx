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
    private Button btnCond;

    @FXML
    private Button btnOffer;

    @FXML
    private AnchorPane view_pages;

    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        if(event.getSource() == btnOffer) {
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionOffer.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }else if(event.getSource() == btnCond) {
            Parent fxml= FXMLLoader.load(getClass().getResource("gestionCondidature.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }
    }
}