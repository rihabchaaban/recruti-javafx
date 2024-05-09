package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Publication;
import models.User;
import services.PublicationService;
import services.userService;

import javax.swing.text.html.ImageView;
import java.awt.*;



public class HelloFrontR {
    @FXML
    private Button RV;

    @FXML
    private ImageView background;

    @FXML
    private Button event;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Button lib;

    @FXML
    private AnchorPane listEventFront;

    @FXML
    private Button offer;
    Parent fxml= null;



    @FXML
    void btnRV(ActionEvent event) throws IOException {

        User loggedInUser = userService.getUser();


        if(loggedInUser.getRole().equals("Recruter")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/DisplayPlace.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Recruti");
            stage.setScene(new Scene(fxml));
            stage.showAndWait();
        }

    }

    @FXML
    void btnevent(ActionEvent event) throws IOException {
        User loggedInUser = userService.getUser();

        if (loggedInUser.getRole().equals("Recruter")){
        Parent fxml = FXMLLoader.load(getClass().getResource("/listEventFront.fxml"));

        // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
        Stage newStage = new Stage();
        newStage.setTitle("Recruti");
        newStage.setScene(new Scene(fxml));

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Afficher la nouvelle fenêtre
        newStage.showAndWait();} else if (loggedInUser.getRole().equals("Condidate")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/listEventFrontCondidat.fxml"));

            // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
            Stage newStage = new Stage();
            newStage.setTitle("Recruti");
            newStage.setScene(new Scene(fxml));

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            newStage.showAndWait();

        }

    }

    @FXML
    void btnlib(ActionEvent event) throws IOException {
        User loggedInUser = userService.getUser();
        if (loggedInUser.getRole().equals("Recruter")){
        Parent fxml = FXMLLoader.load(getClass().getResource("/listBiblioFrontRecruteur.fxml"));

        // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
        Stage newStage = new Stage();
        newStage.setTitle("Recruti");
        newStage.setScene(new Scene(fxml));

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Afficher la nouvelle fenêtre
        newStage.showAndWait();} else if (loggedInUser.getRole().equals("Condidate")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/listBiblioFront.fxml"));

            // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
            Stage newStage = new Stage();
            newStage.setTitle("Recruti");
            newStage.setScene(new Scene(fxml));

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            newStage.showAndWait();

        } ;


    }

    @FXML
    void btnoffer(ActionEvent event) throws IOException {
        User loggedInUser = userService.getUser();

        if (loggedInUser.getRole().equals("Recruter")){
        Parent fxml = FXMLLoader.load(getClass().getResource("/listOfferFrontRecruteur.fxml"));

        // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
        Stage newStage = new Stage();
        newStage.setTitle("Recruti");
        newStage.setScene(new Scene(fxml));

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Afficher la nouvelle fenêtre
        newStage.showAndWait();} else if (loggedInUser.getRole().equals("Condidate")) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/listOfferFront.fxml"));

            // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
            Stage newStage = new Stage();
            newStage.setTitle("Recruti");
            newStage.setScene(new Scene(fxml));

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            newStage.showAndWait();

        } ;

    }

    @FXML
    void profile(MouseEvent event) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/Profile.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Recruti");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();

    }
}
