package controllers;

import models.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.EventService;
import services.ParticipationService;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class listEventFrontCardController implements Initializable {
    @FXML
    private Label labelContactEvent;

    @FXML
    private Label labelDateEvent;

    @FXML
    private Label labelHeureEvent;

    @FXML
    private Label labelLieuEvent;

    @FXML
    private Label labelThemeEvent;

    @FXML
    private Label labelTitreEvent;

    @FXML
    private ImageView labelimgEvent;

    @FXML
    private TextField txtNBRP;


    public static int id;
    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    Event event;


    public void setData (Event event) {
        this.event = event;



        labelTitreEvent.setText(event.getNom_e());
        labelHeureEvent.setText(event.getHeure_e());
        labelLieuEvent.setText(event.getLieu_e());
        labelDateEvent.setText(event.getDate_e());
        labelThemeEvent.setText(event.getTheme_e());
        labelContactEvent.setText(event.getContact_e());
        labelimgEvent.setImage(new Image("file:/C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/" + event.getImage_e()));
        ParticipationService participationService = new ParticipationService();
        long participationCount = participationService.getParticipationCountByEventId(event.getId());
        txtNBRP.setText(String.valueOf(participationCount));
        this.id=event.getId();
        System.out.println(this.id+"////////////////////////////////////////////////////////");

    }

    public Event getData (Event event){
        this.event = event;
        return this.event;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    @FXML
    void open_UpdateEvent(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/EditEventFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Condidature");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }



    @FXML
    void supprimerEvent(ActionEvent event) {
        EventService os = new EventService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet evenement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'offre sélectionnée
            Event e = this.event;


            // Supprimer l'offre de la base de données
            os.delete(e);

        }

    }
    public void generateEventDetailsPDF(Event event) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Ajout de l'image en arrière-plan de la page
            BufferedImage bufferedImage = ImageIO.read(new File("C:/Users/Rihab/Desktop/Workshop-JDBC-JavaFX-master/src/main/java/uploads/" + event.getImage_e()));
            PDImageXObject pdImageXObject = PDImageXObject.createFromByteArray(document, imageToByteArray(bufferedImage), event.getImage_e());

            try (PDPageContentStream backgroundStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                float scale = 0.3f; // Modifier cette valeur pour ajuster la taille de l'image
                float width = pdImageXObject.getWidth() * scale;
                float height = pdImageXObject.getHeight() * scale;
                backgroundStream.drawImage(pdImageXObject, 60, 200, width, height);
            }

            // Ajout du contenu textuel
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            float textStartY = 700; // Position Y de départ du texte
            float textYOffset = 30; // Décalage vertical entre les lignes de texte
            float currentY = textStartY; // Position Y actuelle du contenu

            contentStream.beginText();
            contentStream.newLineAtOffset(100, currentY);
            contentStream.showText("Titre de l'événement : " + event.getNom_e());
            currentY -= textYOffset;
            contentStream.newLineAtOffset(0, -textYOffset);
            contentStream.showText("Date : " + event.getDate_e());
            currentY -= textYOffset;
            contentStream.newLineAtOffset(0, -textYOffset);
            contentStream.showText("Heure : " + event.getHeure_e());
            currentY -= textYOffset;
            contentStream.newLineAtOffset(0, -textYOffset);
            contentStream.showText("Lieu : " + event.getLieu_e());
            currentY -= textYOffset;
            contentStream.newLineAtOffset(0, -textYOffset);
            contentStream.showText("Thème : " + event.getTheme_e());
            currentY -= textYOffset;
            contentStream.newLineAtOffset(0, -textYOffset);
            contentStream.showText("Contact : " + event.getContact_e());
            contentStream.endText();
            contentStream.close();

            String userHome = System.getProperty("user.home");
            Path desktopPath = Paths.get(userHome, "Desktop");
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Timestamp actuel
            Path pdfPath = desktopPath.resolve("event_details_" + timestamp + ".pdf"); // Nom de fichier avec timestamp

            if (!Files.exists(desktopPath)) {
                Files.createDirectory(desktopPath);
            }

            document.save(pdfPath.toFile());
            document.close();

            System.out.println("PDF generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] imageToByteArray(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }
    @FXML
    void generatePDF(ActionEvent event) {
        generateEventDetailsPDF(this.event);
    }

}
