package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Place;
import models.RendezVous;
import services.RendezVousService;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RendezVousEditController {
    @FXML
    private TextField heuresField;

    @FXML
    private ComboBox<String> placeCB;

    @FXML
    private TextField emailCanField;
    @FXML
    private TextField emailRepField;

    @FXML
    private DatePicker dateField;

    private Stage dialogStage;
    private RendezVousService rendezVousService = new RendezVousService();
    private RendezVous rendezVous;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void initialize() {
        List<Place> places = RendezVousService.getAllPlaces();
        ObservableList<String> cityNames = FXCollections.observableArrayList(
                places.stream().map(Place::getVille).collect(Collectors.toList())
        );
        placeCB.setItems(cityNames);
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
        heuresField.setText(rendezVous.getHeure_rendez());
        emailCanField.setText(rendezVous.getEmail_condi());
        emailRepField.setText(rendezVous.getEmail_represen());

        Date sqlDate = rendezVous.getDate_rendez();
        if (sqlDate != null) {
            Instant instant = Instant.ofEpochMilli(sqlDate.getTime());
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            dateField.setValue(localDate);
        }
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            // Retrieve the selected city from the ComboBox
            String selectedCity = placeCB.getValue();

            // Retrieve the corresponding Place object using the selected city
            Place selectedPlace = RendezVousService.getAllPlaces()
                    .stream()
                    .filter(place -> place.getVille().equals(selectedCity))
                    .findFirst()
                    .orElse(null);

            if (selectedPlace != null) {
                // Set the selected place's ID to the rendezVous object
                rendezVous.setId_place(selectedPlace.getId());

                // Set other attributes of rendezVous object
                rendezVous.setHeure_rendez(heuresField.getText());
                rendezVous.setEmail_represen(emailRepField.getText());
                rendezVous.setEmail_condi(emailCanField.getText());

                LocalDate localDate = dateField.getValue();
                Date sqlDate = localDate != null ? Date.valueOf(localDate) : null;
                rendezVous.setDate_rendez(sqlDate);

                // Update the rendezvous in the database
                rendezVousService.update(rendezVous);

                dialogStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Selected place not found",
                        "The selected place could not be found. Please try again.");
            }
        }
    }


    private boolean isInputValid() {
        String errorMessage = "";

        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (heuresField.getText() == null || heuresField.getText().isEmpty()) {
            errorMessage += "No heure !\n";
        }

        if (emailCanField.getText() == null || emailCanField.getText().isEmpty() ||
                !Pattern.matches(emailPattern, emailCanField.getText())) {
            errorMessage += "Invalid or empty candidate email!\n";
        }

        if (emailRepField.getText() == null || emailRepField.getText().isEmpty() ||
                !Pattern.matches(emailPattern, emailRepField.getText())) {
            errorMessage += "Invalid or empty representative email!\n";
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dateField.getValue();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            errorMessage += "Date must be greater than or equal to today!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Fields", "Please correct invalid fields!", errorMessage);
            return false;
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
