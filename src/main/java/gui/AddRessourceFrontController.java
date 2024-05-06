package gui;

import entities.Biblio;
import entities.Ressource;
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
import services.RessourceService;

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

public class AddRessourceFrontController implements Initializable {
    @FXML
    private AnchorPane addRessourceFrontPane;

    @FXML
    private Button btnAddRessource;

    @FXML
    private Button btnClearRessource;

    @FXML
    private TextField txtTitreRessource;

    @FXML
    private ComboBox<String> txtTypeRessource;
    @FXML
    private TextField txtNewType;

    @FXML
    private ComboBox<String> txtCategorieRessource;

    @FXML
    private TextField txtNewCategorie;

    @FXML
    private TextArea txtDescriptionRessource;

    @FXML
    private DatePicker datePickerPublication;

    @FXML
    private ImageView imageViewRessource;

    @FXML
    private Label lblTitreMessage;

    @FXML
    private Label lblCategorieMessage;

    @FXML
    private Label lblTypeMessage;

    @FXML
    private Label lblDescriptionMessage;

    @FXML
    private Label lblDateMessage;

    @FXML
    private Label lblDateMessage2;

    @FXML
    private Label lblImageMessage;

    @FXML
    private Label lblBiblioMessage;

    @FXML
    private Button btnRetour;
    private int biblio_id;

    private File selectedImageFile;
    private String imageName = null;
    @FXML
    private ComboBox<Biblio> txtBiblio;
    private BiblioService biblioService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        biblioService = new BiblioService();

        ObservableList<Biblio> biblioOptions = FXCollections.observableArrayList(biblioService.getAllBiblios());
        txtBiblio.setItems(biblioOptions);

        ObservableList<String> categorieOptions = FXCollections.observableArrayList(
                "Fine Arts", "Social Sciences", "Natural Sciences", "History", "Computer Science");
        txtCategorieRessource.setItems(categorieOptions);

        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Book", "Video", "Article", "Image", "PDF Document");
        txtTypeRessource.setItems(typeOptions);

        lblDateMessage2.setVisible(false);
    }


    @FXML
    void ajouterImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageViewRessource.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageViewRessource.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src","main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }
    @FXML
    void addRessource(ActionEvent event) {
        String titre = txtTitreRessource.getText().trim();
        String type = txtTypeRessource.getValue();
        String categorie = txtCategorieRessource.getValue();
        LocalDate datePublicationValue = datePickerPublication.getValue();
        String description = txtDescriptionRessource.getText().trim();

        Biblio selectedBiblio = txtBiblio.getValue();
        // Vérifiez si tous les champs obligatoires sont remplis
        boolean fieldsEmpty = titre.isEmpty() || type == null || categorie == null || datePublicationValue == null || imageName == null || description.isEmpty();

        if (event.getSource() == btnAddRessource) {
            if (fieldsEmpty) {
                lblTitreMessage.setText(titre.isEmpty() ? "Please enter the title of the resource" : "");
                lblTypeMessage.setText(type == null ? "Please select the type of the resource" : "");
                lblCategorieMessage.setText(categorie == null ? "Please select the category of the resource" : "");
                lblDateMessage.setText(datePublicationValue == null ? "Please choose the publication date of the resource" : "");
                lblDescriptionMessage.setText(description.isEmpty() ? "Please enter the description of the resource" : "");
                lblImageMessage.setText(imageName == null ? "Please select an image for the resource" : "");
                lblBiblioMessage.setText(selectedBiblio == null ? "Please select a library" : ""); // Ajout du contrôle pour la bibliothèque
                return; // Sortir de la méthode si des champs sont vides
            }
// Récupérer l'identifiant de la bibliothèque sélectionnée
            Biblio selectedBiblios = txtBiblio.getValue();
            biblio_id = selectedBiblio.getId(); // Supposons que getId() renvoie l'identifiant de la bibliothèque

            // Obtenir la date d'aujourd'hui
            LocalDate currentDate = LocalDate.now();

            // Comparer les valeurs numériques de l'année, du mois et du jour
            if (!datePublicationValue.isEqual(currentDate)) {
                lblDateMessage2.setText("Please choose a valid date.");
                lblDateMessage2.setVisible(true); // Rendre le message d'erreur de date invalide visible
                return;
            }

            // Ajouter la ressource
            addRessource();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Added Successfully");
            alert.setHeaderText(null);
            alert.setContentText("Your resource has been added successfully.");
            Optional<ButtonType> option = alert.showAndWait();
            clearFieldsRessource();
        }
    }


    private void addRessource() {
        // Récupérer les valeurs des champs
        String titre = txtTitreRessource.getText().trim();
        String type = txtTypeRessource.getValue();
        String categorie = txtCategorieRessource.getValue();
        String description = txtDescriptionRessource.getText().trim();
        String image = imageName;

        // Convertir la valeur de DatePicker en Date
        Date datePublication = null;
        try {
            LocalDate localDate = datePickerPublication.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                datePublication = Date.from(instant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Créer un objet Ressource
        Ressource ressource = new Ressource(biblio_id, titre, type, datePublication, categorie, description, image);

        // Ajouter la ressource à la base de données
        // Remplacez BiblioService par le service approprié pour gérer les ressources
        RessourceService ressourceService = new RessourceService();
        ressourceService.ajouter(ressource);
    }

    @FXML
    void clearFieldsRessource() {
        txtTitreRessource.clear();
        txtTypeRessource.setValue(null);
        txtCategorieRessource.setValue(null);
        datePickerPublication.setValue(null);
        txtDescriptionRessource.clear();
        imageViewRessource.setImage(null);
    }


    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == btnRetour){
            Parent fxml = FXMLLoader.load(getClass().getResource("listBiblioFrontRecruteur.fxml"));
            Scene scene = new Scene(fxml);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void ajouterType(ActionEvent event) {
        String nouveauDomaine = txtNewType.getText().trim();
        if (!nouveauDomaine.isEmpty()) {
            txtTypeRessource.getItems().add(nouveauDomaine);
            txtNewType.clear(); // Efface le champ de saisie après l'ajout
        }
    }
    @FXML
    void ajouterCategorie(ActionEvent event) {
        String nouveauCategorie = txtNewCategorie.getText().trim();
        if (!nouveauCategorie.isEmpty()) {
            txtCategorieRessource.getItems().add(nouveauCategorie);
            txtNewCategorie.clear(); // Efface le champ de saisie après l'ajout
        }
    }



    private void refrech() {
    }
}




