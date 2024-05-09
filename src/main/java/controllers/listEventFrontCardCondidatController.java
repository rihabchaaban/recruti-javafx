package controllers;

import models.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class listEventFrontCardCondidatController implements Initializable {
    @FXML
    private Button btnParticiper;

    @FXML
    private Label labelContactEvent;

    @FXML
    private Label labelDateEvent;

    @FXML
    private Label labelHeureEvent;

    @FXML
    private Label labelLieuEvent;

    @FXML
    private Label labelThemeEvent;

    @FXML
    private Label labelTitreEvent;

    @FXML
    private ImageView labelimgEvent;



    public static int id;

    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    Event event;
    public Event selectedEvent;
    public void setData (Event event) {
        this.event = event;
        this.selectedEvent = event;


        labelTitreEvent.setText(event.getNom_e());
        labelHeureEvent.setText(event.getHeure_e());
        labelLieuEvent.setText(event.getLieu_e());
        labelDateEvent.setText(event.getDate_e());
        labelThemeEvent.setText(event.getTheme_e());
        labelContactEvent.setText(event.getContact_e());
        labelimgEvent.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/" + event.getImage_e()));
        this.id=event.getId();
        System.out.println(this.id+"////////////////////////////////////////////////////////");

    }

    public  Event getSelectedEvent() {
        return selectedEvent;
    }
    public Event getData (Event event){
        this.event = event;
        return this.event;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_Add_Participer(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/AddParticipationFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();

    }






    }
