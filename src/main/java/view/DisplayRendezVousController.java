package view;

import entity.Place;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import entity.RendezVous;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidevclient.services.RendezVousService;

import java.io.IOException;
import java.util.List;

public class DisplayRendezVousController {

    @FXML
    private VBox rendezVousList;

    private RendezVousService rendezVousService;


    public DisplayRendezVousController() {
        rendezVousService = new RendezVousService();
    }

    @FXML
    public void initialize() {

        List<RendezVous> rendezVousData = rendezVousService.getAll();
        displayRendezVous(rendezVousData);

    }

    private void displayRendezVous(List<RendezVous> rendezVousData) {
        for (RendezVous rendezVous : rendezVousData) {
            Label dateLabel = new Label("Date: " + rendezVous.getDate_rendez().toString());
            Label heureLabel = new Label("Heure: " + rendezVous.getHeure_rendez());
            Label emailCandidatLabel = new Label("Email Candidat: " + rendezVous.getEmail_condi());
            Label emailRepresentantLabel = new Label("Email Representant: " + rendezVous.getEmail_represen());

            Place place = rendezVousService.getPlaceByID(rendezVous.getId_place());
            String placeInfo = place != null ? place.getVille() : "Unknown";

            Label placeLabel = new Label("Place Ville: " + placeInfo);

            HBox buttonBox = new HBox();
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> deleteRendezVous(rendezVous.getId()));
            Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> updateRendezVous(rendezVous.getId()));
            buttonBox.getChildren().addAll(deleteButton, updateButton);

            VBox rendezVousBox = new VBox(dateLabel, heureLabel, emailCandidatLabel, emailRepresentantLabel, placeLabel, buttonBox);
            rendezVousBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px;");

            rendezVousList.getChildren().add(rendezVousBox);
        }
    }



    private void deleteRendezVous(int id) {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Delete RendezVous");
        confirmation.setContentText("Are you sure you want to delete this RendezVous?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                rendezVousService.delete(id);

                refreshRendezVousList();
            }
        });
    }


    void refreshRendezVousList() {
        rendezVousList.getChildren().clear();
        List<RendezVous> rendezVousData = rendezVousService.getAll();
        displayRendezVous(rendezVousData);
    }


    private void updateRendezVous(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RendezVousEditInterface.fxml"));
            Parent root = loader.load();

            Stage editStage = new Stage();
            editStage.setTitle("Edit RendezVous");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.setScene(new Scene(root));

            RendezVousEditController controller = loader.getController();
            controller.setDialogStage(editStage);

            RendezVous selectedRendezVous = rendezVousService.getById(id);
            if (selectedRendezVous != null) {
                controller.setRendezVous(selectedRendezVous);
                editStage.showAndWait();

                refreshRendezVousList();
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to retrieve RendezVous", "Unable to retrieve the selected RendezVous for editing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open edit window", e.getMessage());
        }
    }

    @FXML
    private void navigateToAddInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RendezVousAddInterface.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("New commade");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.setScene(new Scene(root));

            RendezVousAddController controller = loader.getController();
            controller.setDialogStage(newStage);
            controller.setDisplayRendezVousController(this);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new RendezVous window", e.getMessage());
        }

    }
    @FXML
    private void navigateToPlaceInterface() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayPlace.fxml"));
            Parent root = loader.load();

            Stage placeStage = new Stage();
            placeStage.setTitle("Place Interface");
            placeStage.setScene(new Scene(root));
            placeStage.show();
        } catch (IOException e) {

            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open Place Interface", e.getMessage());
        }
    }




    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
