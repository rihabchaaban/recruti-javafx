package controllers;

import models.Condidature;
import models.Offer;
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
import services.CondidatureService;
import services.OfferService;

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
import java.util.logging.Level;
import java.util.logging.Logger;




public class AddCondidatureController implements Initializable {

    @FXML
    private ImageView txtCvCond;

    @FXML
    private TextField txtEmailCond;

    @FXML
    private TextArea txtLettreCond;

    @FXML
    private TextField txtNomCond;

    @FXML
    private Button btnAddCond;

    @FXML
    private Button btnClearCond;


    private int idOffre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/itemOffer.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox vBox1 = (VBox) anchorPane.getChildren().get(0);
            itemOfferController itemController = fxmlLoader.getController();
            this.idOffre=itemController.getId();
        } catch (IOException ex) {
            Logger.getLogger(itemOfferController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File selectedImageFile;
    private String imageName = null ;


    @FXML
    void ajouterImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(txtCvCond.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            txtCvCond.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src","main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        }
    }

    @FXML
    void addCondidature(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddCond){
            if (txtEmailCond.getText().isEmpty() || txtLettreCond.getText().isEmpty()
                    || txtNomCond.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Condidature.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajouterCondidature();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Condidature a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
                clearFieldsCondidature();
            }
        }
        if(event.getSource() == btnClearCond){
            clearFieldsCondidature();
        }
    }

    @FXML
    void clearFieldsCondidature() {
        txtCvCond.setImage(null);
        txtEmailCond.clear();
        txtNomCond.clear();
        txtLettreCond.clear();
    }

    private void ajouterCondidature() {

        // From Formulaire
        String nom = txtNomCond.getText();
        String email = txtEmailCond.getText();
        String cv = imageName;
        String lettre = txtLettreCond.getText();
        int offre_id=this.idOffre;
        int user_id=1;

        Condidature c = new Condidature(
                offre_id, nom, email, cv, lettre, user_id);
        CondidatureService cs = new CondidatureService();
        cs.ajouter(c);
    }





}
