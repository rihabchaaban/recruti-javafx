package gui;

import entities.Biblio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.BiblioService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddBiblioController implements Initializable {

    @FXML
    private AnchorPane addBiblioPane;

    @FXML
    private Button btnAddBiblio;

    @FXML
    private Button btnClearBiblio;

    @FXML
    private TextField txtNewDomain;

    @FXML
    private TextField txtNomBiblio;

    @FXML
    private ComboBox<String> txtDomaineBiblio;

    @FXML
    private DatePicker txtDateBiblio;

    @FXML
    private ImageView imageViewBiblio;

    @FXML
    private Label lblNomMessage;

    @FXML
    private Label lblDomaineMessage;

    @FXML
    private Label lblDateMessage;

    @FXML
    private Label lblDateMessage2;

    @FXML
    private Label lblImageMessage;

    @FXML
    private Button btnRetour;

    private File selectedImageFile;
    private String imageName = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Business And Management", "Fine Arts", "Medical Science", "Engineering",
                "Natural Science", "Music And Performing Arts", "History", "Design");
        txtDomaineBiblio.setItems(options);
        lblDateMessage2.setVisible(false); // Cacher le message d'erreur de date invalide
    }


    @FXML
    void ajouterImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageViewBiblio.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageViewBiblio.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }

    @FXML
    void addBiblio(ActionEvent event) {
        String nomBiblio = txtNomBiblio.getText().trim();
        String domaineBiblio = txtDomaineBiblio.getValue();
        LocalDate dateCreationValue = txtDateBiblio.getValue();

        // Vérifier si tous les champs sont remplis
        boolean fieldsEmpty = nomBiblio.isEmpty() || domaineBiblio == null || dateCreationValue == null || imageName == null;

        if (event.getSource() == btnAddBiblio) {
            if (fieldsEmpty) {
                lblNomMessage.setText(nomBiblio.isEmpty() ? "Please enter the name of the library" : "");
                lblDomaineMessage.setText(domaineBiblio == null ? "Please select the domain of the library"  : "");
                lblDateMessage.setText(dateCreationValue == null ? "Please choose the creation date of the library" : "");
                lblImageMessage.setText(imageName == null ? "Please select an image for the library" : "");
                return; // Sortir de la méthode si des champs sont vides
            }

            // Obtenir la date d'aujourd'hui
            LocalDate currentDate = LocalDate.now();

            // Comparer les valeurs numériques de l'année, du mois et du jour
            if (!dateCreationValue.isEqual(currentDate)) {
                lblDateMessage2.setText("Please choose a valid date.");
                lblDateMessage2.setVisible(true); // Rendre le message d'erreur de date invalide visible
                return;
            }


            // Ajouter la bibliothèque
            addBiblio();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Added Successfully");
            alert.setHeaderText(null);
            alert.setContentText("Your library has been added successfully.");
            Optional<ButtonType> option = alert.showAndWait();
            clearFieldsBiblio();
        }

        // Effacer les champs si le bouton "Clear" est cliqué
        if (event.getSource() == btnClearBiblio) {
            clearFieldsBiblio();
        }
    }

    @FXML
    void clearFieldsBiblio() {
        txtNomBiblio.clear();
        txtDomaineBiblio.setValue("Choose Domain");
        txtDateBiblio.setValue(null);
        imageViewBiblio.setImage(null);
    }

    private void addBiblio() {
        // From Formulaire
        String nom = txtNomBiblio.getText();
        String domaine = txtDomaineBiblio.getValue();
        String image = imageName;

        // Convert DatePicker value to Date
        Date dateCreation = null;
        try {
            LocalDate localDate = txtDateBiblio.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateCreation = Date.from(instant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Biblio biblio = new Biblio(nom, domaine, dateCreation, image);
        BiblioService biblioService = new BiblioService();
        biblioService.ajouter(biblio);
    }

    // Méthode pour retourner à la page gestionBiblio.fxml

    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnRetour){
            Parent fxml = FXMLLoader.load(getClass().getResource("gestionBiblio.fxml"));
            addBiblioPane.getChildren().removeAll();
            addBiblioPane.getChildren().setAll(fxml);
        }
    }

    @FXML
    void ajouterDomaine(ActionEvent event) {
        String nouveauDomaine = txtNewDomain.getText().trim();
        if (!nouveauDomaine.isEmpty()) {
            txtDomaineBiblio.getItems().add(nouveauDomaine);
            txtNewDomain.clear(); // Efface le champ de saisie après l'ajout
        }
    }

}
