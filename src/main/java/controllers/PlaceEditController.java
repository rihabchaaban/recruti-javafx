package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Place;
import services.PlaceService;

public class PlaceEditController {

    @FXML
    private TextField gouvernementF;

    @FXML
    private TextField villeF;
    @FXML
    private TextField altitudeF;
    @FXML
    private TextField longitudeF;



    private Stage dialogStage;
    private PlaceService placeService = new PlaceService();
    private Place place;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setPlace(Place place) {
        this.place = place;
        gouvernementF.setText(place.getGouvernement());
        villeF.setText(place.getVille());
        altitudeF.setText(Float.toString(place.getAltitude()));
        longitudeF.setText(Float.toString(place.getLongitude()));
    }

    @FXML
    private void handleUpdate() {
        String gouvernement = gouvernementF.getText().trim();
        String ville = villeF.getText().trim();
        String altitudeStr = altitudeF.getText().trim();
        String longitudeStr = longitudeF.getText().trim();

        if (gouvernement.isEmpty() || ville.isEmpty() || altitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }

        float altitude;
        float longitude;

        try {
            altitude = Float.parseFloat(altitudeStr);
            longitude = Float.parseFloat(longitudeStr);
        } catch (NumberFormatException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Invalid Input", "Altitude and Longitude must be valid numbers.");
            return;
        }

        place.setGouvernement(gouvernement);
        place.setVille(ville);
        place.setAltitude(altitude);
        place.setLongitude(longitude);

        placeService.update(place);
        dialogStage.close();
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
