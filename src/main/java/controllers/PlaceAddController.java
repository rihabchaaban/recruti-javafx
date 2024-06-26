package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Place;
import services.IService2;
import services.PlaceService;

import java.sql.SQLException;

public class PlaceAddController {

    @FXML
    private TextField longitudeTv;
    @FXML
    private TextField altitudeTv;
    @FXML
    private ComboBox<?> stateComboBox;
    @FXML
    private TextField gouvernementTv;


    private Stage dialogStage;
    private Place place = new Place();
    private boolean hasClicked = false;
    private IService2<Place> placeService = new PlaceService();
    private DisplayPlaceController displayPlaceController;

    public void setDisplayPlaceController(DisplayPlaceController displayPlaceController) {
        this.displayPlaceController = displayPlaceController;
    }

    @FXML
    private void initialize() {

    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }




    @FXML
    private void handleOk() throws SQLException {
        String gouvernement = gouvernementTv.getText().trim();
        String ville = (String) stateComboBox.getValue();
        String altitudeStr = altitudeTv.getText().trim();
        String longitudeStr = longitudeTv.getText().trim();

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

        placeService.create(place);
        dialogStage.close();

        if (displayPlaceController != null) {
            displayPlaceController.refreshPlaceList();
            openPlaceListInterface();
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void openPlaceListInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RendezVousAddInterface.fxml"));
            Parent root = loader.load();

            Stage listStage = new Stage();
            listStage.setTitle("Place List");
            listStage.setScene(new Scene(root));

            listStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }


}
