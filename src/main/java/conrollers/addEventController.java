package conrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.EventService;
import models.Event;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class addEventController implements Initializable {

    @FXML
    private AnchorPane addEventPane;

    @FXML
    private Button btnAddEvent;

    @FXML
    private Button btnClearEvent;

    @FXML
    private TextField nameeventtxt;

    @FXML
    private TextField txtContactEvent;

    @FXML
    private DatePicker txtDateEvent;

    @FXML
    private TextArea txtDescriptionEvent;

    @FXML
    private ImageView txtEventImage;

    @FXML
    private TextField txtHeureEvent;

    @FXML
    private TextField txtLocalisationEvent;

    @FXML
    private ComboBox<String> txtThemeEvent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "info","Genie civil","Electro");
        txtThemeEvent.setItems(options);

    }

    private File selectedImageFile;
    private String imageName = null ;
    @FXML
    void ajouterImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(txtEventImage.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            txtEventImage.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destinationDir = Paths.get("C:", "Users", "hamou", "gestionEvent", "gestionEvent", "src", "main", "java", "uploads");


            if (!Files.exists(destinationDir)) {
                try {
                    Files.createDirectories(destinationDir);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Gérer l'erreur de création du répertoire
                    return;
                }
            }

            Path destination = destinationDir.resolve(imageName);
            try {
                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur de copie de fichier
            }
        }
    }

    @FXML
    void ajouterEvent(ActionEvent event) {
        if (!validateInputs())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre événement.");
            Optional<ButtonType> option = alert.showAndWait();
        } else {
            String nom = nameeventtxt.getText();
            String contact = txtContactEvent.getText();
            String date = txtDateEvent.getValue().toString();
            String description = txtDescriptionEvent.getText();
            String heure = txtHeureEvent.getText();

            String localisation = txtLocalisationEvent.getText();
            String theme = txtThemeEvent.getValue();
            String image = imageName;
            Event eventToAdd = new Event(nom,heure,localisation,description,image,theme,contact,date);


            // Ajouter l'événement à la base de données
            EventService eventService = new EventService();
            eventService.add(eventToAdd);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajouté avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre événement a été ajouté avec succès.");
            Optional<ButtonType> option = alert.showAndWait();

            // Effacer les champs après l'ajout
            clearFieldsEvent();
        }
    }
    private boolean validateInputs() {
        String nom = nameeventtxt.getText();
        String contact = txtContactEvent.getText();
        LocalDate date = txtDateEvent.getValue();
        String description = txtDescriptionEvent.getText();
        String heure = txtHeureEvent.getText();

        String localisation = txtLocalisationEvent.getText();
        String theme = txtThemeEvent.getValue();

        // Valider les champs
        if (nom.isEmpty() || contact.isEmpty() || date == null || description.isEmpty() ||
                heure.isEmpty()  || localisation.isEmpty() || theme == null) {
            showAlert("Information manquante", "Vous devez remplir tous les détails concernant votre événement.");
            return false;
        }

        // Valider le format de l'e-mail (exemple)
        if (!contact.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Format incorrect", "Veuillez entrer une adresse e-mail valide.");
            return false;
        }

        // Valider d'autres champs selon vos besoins...

        return true; // Toutes les validations réussies
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void clearFieldsEvent() {
        nameeventtxt.clear();
        txtContactEvent.clear();
        txtDateEvent.setValue(null);
        txtDescriptionEvent.clear();
        txtHeureEvent.clear();
        txtEventImage.setImage(null);
        txtLocalisationEvent.clear();
        txtThemeEvent.getSelectionModel().clearSelection();
    }
}
