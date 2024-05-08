package conrollers;

import models.Event;
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
import services.EventService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventFrontController {

    @FXML
    private Button btnClearEvent;

    @FXML
    private Button btnUpdateEvent;

    @FXML
    private TextField txtContactEvent1;

    @FXML
    private DatePicker txtDateEvent;

    @FXML
    private TextArea txtDescriptionEvent;

    @FXML
    private TextField txtHeureEvent;

    @FXML
    private ImageView txtImageEvent;

    @FXML
    private TextField txtLocalisationEvent;

    @FXML
    private ComboBox<String> txtThemeEvent;

    @FXML
    private TextField txtTitreEvent;

    public static int id;



    private Event eventToUpdate1;


    Event event;


    public void initData(Event event) {
        eventToUpdate1 = event;
        // Utiliser eventToUpdate pour initialiser les champs dans l'interface graphique
        ObservableList<String> themes = FXCollections.observableArrayList(
                "Thème 1", "Thème 2", "Thème 3"); // Adapter les thèmes selon vos besoins
        txtThemeEvent.setItems(themes);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("itemEvent.fxml"));

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            VBox hBox = (VBox) anchorPane.getChildren().get(0);
            itemEventController item = fxmlLoader.getController();
            EventService os = new EventService();

            txtTitreEvent.setText(String.valueOf(eventToUpdate1.getNom_e()));
            txtContactEvent1.setText(eventToUpdate1.getContact_e());
            //txtDateEvent.setValue(LocalDate.parse(event.getDate_e())); // Supposant que la date est stockée au format String (ISO_LOCAL_DATE)
            txtDescriptionEvent.setText(eventToUpdate1.getDescription());
            txtHeureEvent.setText(eventToUpdate1.getHeure_e());
            txtImageEvent.setImage(new Image("file:///C:/Users/hamou/gestionEvent/gestionEvent/src/main/java/uploads/"+eventToUpdate1.getImage_e()));
            imageName = eventToUpdate1.getImage_e();
            txtLocalisationEvent.setText(eventToUpdate1.getLieu_e());
            txtThemeEvent.setValue(eventToUpdate1.getTheme_e());

        } catch (IOException ex) {
            Logger.getLogger(itemEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void updateEvent(ActionEvent event) {
        if (txtThemeEvent.getValue().isEmpty() || txtLocalisationEvent.getText().isEmpty() || txtContactEvent1.getText().isEmpty()
                || txtTitreEvent.getText().isEmpty() || txtDescriptionEvent.getText().isEmpty() || txtHeureEvent.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Evenement.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifierEvent();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Evenement a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
        if(event.getSource() == btnClearEvent){
            clearFieldsEvent();
        }
    }

    private File selectedImageFile;

    private String imageName = null ;
    @FXML
    void updateImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(txtImageEvent.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            txtImageEvent.setImage(image);

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
    void clearFieldsEvent() {
        txtDateEvent.getEditor().clear();
        txtDescriptionEvent.clear();
        txtHeureEvent.clear();
        txtTitreEvent.clear();
        txtImageEvent.setImage(null);
        txtLocalisationEvent.clear();
        txtContactEvent1.clear();
        txtThemeEvent.setValue("Choisir le type");
    }

    void modifierEvent(){
        // From Formulaire
        String titre = txtTitreEvent.getText();
        String description = txtDescriptionEvent.getText();
        String type = txtThemeEvent.getValue();
        String localisation = txtLocalisationEvent.getText();
        String date = String.valueOf(txtDateEvent.getValue());

        String heure = txtHeureEvent.getText();
        String contact= txtContactEvent1.getText();
        String image = imageName;


        Event e = new Event(eventToUpdate1.getId(), titre, heure, localisation, description, image , type, contact , date);
        EventService os = new EventService();
        try {
            os.update(e);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
