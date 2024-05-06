package gui;

import entities.Biblio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.BiblioService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditBiblioController implements Initializable {

    @FXML
    private Button btnClearBiblio;

    @FXML
    private Button btnUpdateBiblio;

    @FXML
    private DatePicker txtDateBiblio;

    @FXML
    private TextField txtNomBiblio;

    @FXML
    private ComboBox<String> txtDomaineBiblio;

    @FXML
    private ImageView imageViewBiblio;

    @FXML
    private AnchorPane updateBiblioPane;

    Biblio biblio;

    private File selectedImageFile;
    private String imageName = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Law Library", "Art Library", "Science Library", "History Library", "Music Library", "Design Library", "Medical Library", "Health Library", "Engineering Library");
        txtDomaineBiblio.setItems(options);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("itemBiblio.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox hBox = (VBox) anchorPane.getChildren().get(0);
            itemBiblioController item = fxmlLoader.getController();

            // Ajout d'une vérification pour s'assurer que item.getId() renvoie une valeur valide
            Logger.getLogger(EditBiblioController.class.getName()).log(Level.SEVERE, "item or item.getId() is null");
        } catch (IOException ex) {
            Logger.getLogger(itemBiblioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void clearFieldsBiblio() {
        txtNomBiblio.clear();
        txtDomaineBiblio.setValue(null);
        txtDateBiblio.setValue(null);
        imageViewBiblio.setImage(null);
    }

    @FXML
    void updateBiblio(ActionEvent event) {
        if (txtNomBiblio.getText().isEmpty() || txtDomaineBiblio.getValue() == null || txtDateBiblio.getValue() == null || imageName == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre bibliothèque.");
            alert.showAndWait();
        } else {
            modifierBiblio();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre bibliothèque a été modifiée avec succès.");
            alert.showAndWait();

            clearFieldsBiblio();
        }
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

    private void modifierBiblio() {
        String nom = txtNomBiblio.getText();
        String domaine = txtDomaineBiblio.getValue();
        LocalDate localDate = txtDateBiblio.getValue();
        Date date = null;
        if (localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
        }

        Biblio updatedBiblio = new Biblio();
        updatedBiblio.setId(biblio.getId());
        updatedBiblio.setNom_b(nom);
        updatedBiblio.setDomaine_b(domaine);
        updatedBiblio.setDate_creation_b(date);
        if (imageName != null) {
            updatedBiblio.setImage_b(imageName);
        } else {
            updatedBiblio.setImage_b(biblio.getImage_b());
        }

        BiblioService bs = new BiblioService();
        bs.modifier(updatedBiblio);
    }
}
