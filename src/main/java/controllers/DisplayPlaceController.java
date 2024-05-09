package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Place;
import services.PlaceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayPlaceController {

    @FXML
    private VBox placeList;
    @FXML
    private ComboBox<String> stateFilterComboBox;

    private PlaceService placeService;


    public DisplayPlaceController() {
        placeService = new PlaceService();
    }


    @FXML
    public void initialize() {
        List<Place> placeData = placeService.getAll();
        for (Place place : placeData) {
            displayPlace(place);
            initializeFilters();
        }
    }
    public void addPlace(Place place) {
        displayPlace(place);
    }


    private void displayPlace(Place place) {
        Label gouvernementLabel = new Label("gouvernement: " + place.getGouvernement());
        Label villeLabel = new Label("ville: " + place.getVille());
        Label altitudeLabel = new Label("altitude: " + place.getAltitude());
        Label longitudeLabel = new Label("longitude: " + place.getLongitude());

        HBox buttonBox = new HBox();
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deletePlace(place.getId()));
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updatePlace(place.getId()));
        buttonBox.getChildren().addAll(deleteButton, updateButton);

        VBox placeBox = new VBox(gouvernementLabel, villeLabel, altitudeLabel, longitudeLabel, buttonBox);
        placeBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px;");

        placeList.getChildren().add(placeBox);
    }


    private void deletePlace(int id) {

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Delete place");
        confirmation.setContentText("Are you sure you want to delete this place?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                placeService.delete(id);

                refreshPlaceList();
            }
        });
    }


    public void refreshPlaceList() {

        placeList.getChildren().clear();

        List<Place> placeData = placeService.getAll();

        for (Place place : placeData) {
            displayPlace(place);
        }
    }


@FXML
private  void navigateToRDVInterface(){
    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayRendezVous.fxml"));
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

    private void updatePlace(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlaceEditInterface.fxml"));
            Parent root = loader.load();

            Stage editStage = new Stage();
            editStage.setTitle("Edit Place");
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setScene(new Scene(root));

            PlaceEditController controller = loader.getController();
            controller.setDialogStage(editStage);

            Place selectedPlace = placeService.getById(id);
            if (selectedPlace != null) {
                controller.setPlace(selectedPlace);
                editStage.showAndWait();

                refreshPlaceList();
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to retrieve Place", "Unable to retrieve the selected Place for editing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open edit window", e.getMessage());
        }
    }

@FXML
private void  map () {
    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/googlemap.fxml"));
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

    @FXML
    private void navigateToAddInterface() {
        try {

            Stage stage = (Stage) placeList.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlaceAddInterface.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("New commade");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.setScene(new Scene(root));

            PlaceAddController controller = loader.getController();
            controller.setDialogStage(newStage);

            controller.setDisplayPlaceController(this);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to open new Place window", e.getMessage());
        }
    }


    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void initializeFilters() {
        ObservableList<String> villeList = FXCollections.observableArrayList(
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan"
        );
        stateFilterComboBox.setItems(villeList);
    }
    @FXML
    private void applyFilters(ActionEvent event) throws SQLException {
        String selectedState = (String) stateFilterComboBox.getValue();

        List<Place> filteredPlaces = placeService.getAll();

        if (selectedState != null && !selectedState.isEmpty()) {
            filteredPlaces = filteredPlaces.stream()
                    .filter(activity -> activity.getVille().equalsIgnoreCase(selectedState))
                    .collect(Collectors.toList());
        }



        placeList.getChildren().clear();

        for (Place  places : filteredPlaces) {
            displayPlace(places);
        }
    }
}
