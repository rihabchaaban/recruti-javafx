package controllers;

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
import models.Biblio;
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

public class EditBiblioFrontController  implements Initializable {

    @FXML
    private AnchorPane updateBiblioPane;

    @FXML
    private Button btnUpdateBiblio;

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
    private Label lblImageMessage;

    @FXML
    private Button btnRetour;

    private File selectedImageFile;
    private String imageName = null;

    private BiblioService biblioService = new BiblioService(); // Créer une instance du service

    private Biblio biblioToEdit; // Stocker la bibliothèque à éditer

    // Setter pour définir la bibliothèque à éditer
    public void setBiblioToEdit(Biblio biblioToEdit) {
        this.biblioToEdit = biblioToEdit;
    }
    Biblio biblio;
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Business And Management", "Fine Arts", "Medical Science", "Engineering",
                "Natural Science", "Music And Performing Arts", "History", "Design");
        txtDomaineBiblio.setItems(options);

        if (biblioToEdit != null) {
            // Afficher les détails de la bibliothèque à éditer
            txtNomBiblio.setText(biblioToEdit.getNom_b());
            txtDomaineBiblio.setValue(biblioToEdit.getDomaine_b());
            // txtDateBiblio.setValue(biblioToEdit.getDate_creation_b());
            // imageViewBiblio.setImage(biblioToEdit.getImage_b());
            biblio = biblioToEdit;}
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
    void updateBiblio(ActionEvent event) {
        modifBiblio();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modifié avec succès");
        alert.setHeaderText(null);
        alert.setContentText("Votre Biblio a été modifié avec succès.");
        Optional<ButtonType> option = alert.showAndWait();
    }

    void modifBiblio() {
        // From Formulaire
        String nom = txtNomBiblio.getText();
        String domaine = txtDomaineBiblio.getValue();
        Date date = null;
        try {
            LocalDate localDate = txtDateBiblio.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date = Date.from(instant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String image = imageName; // Utilisez le nom de l'image au lieu de l'image elle-même

        Biblio b = new Biblio(
                biblioToEdit.getId(), nom, domaine, date, image);
        BiblioService bs = new BiblioService();
        bs.modifier(b);
    }





    @FXML
    void clearFieldsBiblio() {
        txtNomBiblio.clear();
        txtDomaineBiblio.setValue(null);
        txtDateBiblio.setValue(null);
        imageViewBiblio.setImage(null);
    }

    // Méthode pour retourner à la page gestionBiblio.fxml

    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnRetour){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listBiblioFrontRecruteur.fxml"));
            Scene scene = new Scene(fxml);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
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
