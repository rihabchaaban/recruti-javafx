package controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import models.Condidature;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class addCondidatureFrontController implements Initializable {

    @FXML
    private Button btnAddCond;

    @FXML
    private Button btnClearCond;

    @FXML
    private ImageView txtCvCond;

    @FXML
    private TextField txtEmailCond;

    @FXML
    private TextArea txtLettreCond;

    @FXML
    private TextField txtNomCond;

    private int idOffre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/listOfferFrontCard.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox vBox1 = (VBox) anchorPane.getChildren().get(0);
            listOfferFrontCardController itemController = fxmlLoader.getController();
            this.idOffre=itemController.getIdOff();
        } catch (IOException ex) {
            Logger.getLogger(listOfferFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
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
                send_SMS();
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

    void send_SMS(){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte
        String ACCOUNT_SID = "AC4f3f6588092870ca2ff9cd8fb14dc0cd";
        String AUTH_TOKEN = "4416dd1b2d73fb09eafd5e84a1472b4f";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+21629797918";
        String message = "Bonjour Mr ,\n"
                + "Nous sommes ravis de vous informer qu'une condidature a été ajouté.\n "
                + "Veuillez contactez l'administration pour plus de details.\n "
                + "Merci de votre fidélité et à bientôt chez Recruti.\n"
                + "Cordialement,\n"
                + "Recruti";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+13345648101"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}
