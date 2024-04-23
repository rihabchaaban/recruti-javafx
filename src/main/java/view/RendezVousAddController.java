package view;

import entity.Place;
import entity.RendezVous;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pidevclient.services.IService;
import pidevclient.services.RendezVousService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RendezVousAddController {


    @FXML
    private TextField emailCanTv;

    @FXML
    private ComboBox placeCB;

    @FXML
    private TextField emailRepTv;

    @FXML
    private DatePicker dateTv;

    @FXML
    private TextField heureTv;

    private Stage dialogStage;
    private RendezVous rendezVous = new RendezVous();
    private boolean hasClicked = false;
    private IService<RendezVous> rendezVousService = new RendezVousService();
    private DisplayRendezVousController displayRendezVousController;

    public void setDisplayRendezVousController(DisplayRendezVousController displayRendezVousController) {
        this.displayRendezVousController = displayRendezVousController;
    }




    @FXML
    private void initialize() {
        List<Place> places = RendezVousService.getAllPlaces();
        ObservableList<String> cityNames = FXCollections.observableArrayList(
                places.stream().map(Place::getVille).collect(Collectors.toList())
        );
        placeCB.setItems(cityNames);
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRendezVous(RendezVous rendezVous) {
        this.rendezVous = rendezVous;
        heureTv.setText(rendezVous.getHeure_rendez());
        emailCanTv.setText(rendezVous.getEmail_condi());
        emailRepTv.setText(rendezVous.getEmail_represen());

        Date sqlDate = rendezVous.getDate_rendez();
        if (sqlDate != null) {
            Instant instant = Instant.ofEpochMilli(sqlDate.getTime());
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            dateTv.setValue(localDate);
        }
    }

    public boolean isClicked() {
        return hasClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        if (heureTv.getText() == null || heureTv.getText().isEmpty()) {
            errorMessage += "heure field is empty!\n";
        }



        if (emailRepTv.getText() == null || emailRepTv.getText().isEmpty() ||
                !Pattern.matches(emailPattern, emailRepTv.getText())) {
            errorMessage += "Invalid or empty email for representative!\n";
        }

        if (emailCanTv.getText() == null || emailCanTv.getText().isEmpty() ||
                !Pattern.matches(emailPattern, emailCanTv.getText())) {
            errorMessage += "Invalid or empty email for candidate!\n";
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = dateTv.getValue();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            errorMessage += "Date must be greater than or equal to today!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {

            String selectedCity = (String) placeCB.getSelectionModel().getSelectedItem();

            Place selectedPlace = RendezVousService.getAllPlaces()
                    .stream()
                    .filter(place -> place.getVille().equals(selectedCity))
                    .findFirst()
                    .orElse(null);

            if (selectedPlace != null) {

                rendezVous.setId_place(selectedPlace.getId());

                rendezVous.setHeure_rendez(heureTv.getText());
                rendezVous.setEmail_condi(emailCanTv.getText());
                rendezVous.setEmail_represen(emailRepTv.getText());

                LocalDate localDate = dateTv.getValue();
                java.sql.Date sqlDate = localDate != null ? Date.valueOf(localDate) : null;
                rendezVous.setDate_rendez(sqlDate);

                rendezVousService.create(rendezVous);


                if (displayRendezVousController != null) {
                    displayRendezVousController.refreshRendezVousList();
                }

                hasClicked = true;
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error");
                alert.setHeaderText("Selected place not found");
                alert.setContentText("The selected place could not be found. Please try again.");
                alert.showAndWait();
            }
        }
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
