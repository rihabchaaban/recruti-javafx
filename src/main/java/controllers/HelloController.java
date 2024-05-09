package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button btnCond;

    @FXML
    private Button btnOffer;
    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnCond1;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnLibrarie;

    @FXML
    private Button btnPost;

    @FXML
    private Button btnUser;

    @FXML
    public AnchorPane view_pages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent fxml= null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        view_pages.getChildren().removeAll();
        view_pages.getChildren().setAll(fxml);
    }

    @FXML
    public void switchForm(ActionEvent event) throws IOException {

        if(event.getSource() == btnUser) {
            Parent fxml= FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }else if(event.getSource() == btnPost)
        {  Parent fxml= FXMLLoader.load(getClass().getResource("/ListPublications.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
    }else if(event.getSource() == btnOffer) {
        Parent fxml= FXMLLoader.load(getClass().getResource("/gestionOffer.fxml"));
        view_pages.getChildren().removeAll();
        view_pages.getChildren().setAll(fxml);
    }else if(event.getSource() == btnEvent) {
        Parent fxml= FXMLLoader.load(getClass().getResource("/gestionEvent.fxml"));
        view_pages.getChildren().removeAll();
        view_pages.getChildren().setAll(fxml);
    }else {
        Parent fxml= FXMLLoader.load(getClass().getResource("/gestionBiblio.fxml"));
        view_pages.getChildren().removeAll();
        view_pages.getChildren().setAll(fxml);
    }
}


}