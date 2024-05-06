package tn.esprit.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.models.Ban;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Media;
import tn.esprit.models.Publication;
import tn.esprit.services.*;
import javafx.scene.control.Alert;

import java.awt.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.ResourceBundle;
import okhttp3.*;
import okhttp3.Request.Builder;


import javax.mail.MessagingException;

import static javafx.scene.layout.HBox.*;



public class Profile implements Initializable {

    @FXML
    private TextArea contenuPost;


    private PublicationService publicationService;
    @FXML
    private Label username;

    @FXML
    private AnchorPane scrollingArea;


    @FXML
    private VBox mainContainer;

    @FXML
    private VBox pubContainer;

    private static final Logger LOGGER = Logger.getLogger(Profile.class.getName());


    // List to hold media files selected by the user
    private List<File> selectedMediaFiles = new ArrayList<>();

    private String determineMediaType(File file) {
        String fileName = file.getName();
        if (fileName != null && !fileName.isEmpty()) {
            if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png")) {
                return "photo";
            } else if (fileName.toLowerCase().endsWith(".mp4") || fileName.toLowerCase().endsWith(".avi") || fileName.toLowerCase().endsWith(".mov")) {
                return "video";
            }
        }
        // Default to unknown if extension not recognized
        return "unknown";
    }

    @FXML
    void addPost(ActionEvent event) throws IOException, MessagingException {
        String contenu = contenuPost.getText();
        if (contenu.trim().isEmpty()) {
            showAlert("Empty Post Content", "Post content cannot be empty. Please enter some text.");
            return;
        }

        PublicationService publicationService = new PublicationService();
        int publicationId = 0;
        Publication publication = new Publication();
        publication.setUser_id(1);
        publication.setContenu(contenu);

        BanService banService = new BanService();
        Ban ban = banService.getBanByUserId(publication.getUser_id());
        int user_id=publication.getUser_id();

           if (isUserBanned(ban)) {
               showBannedProfileAlert();
               return;
           }

         if(! updateOrAddBan(banService, ban, contenu,user_id)){

        publicationId = publicationService.addAndGetId(publication);
        saveMediaFiles(publicationId);
        redirectToProfilePage(event);
        }
    }

    private boolean isUserBanned(Ban ban) {
        if(ban != null && ban.getDate_ban()!=null)
        {  LocalDateTime dateBan = ban.getDate_ban().toLocalDateTime();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime dateBanPlus2Minutes = dateBan.plusMinutes(3);

        return  ban.getDate_ban() != null && ban.getNb_post() >= 3 && currentDate.isBefore(dateBanPlus2Minutes) ;
    }else{return ban != null && ban.getDate_ban() != null && ban.getNb_post() >= 3;}}

    private boolean isValidContent(String contenu) throws IOException {
        String filteredContent = filterContent(contenu);
        System.out.println(filteredContent);
        return contenu.equals(filteredContent);
    }

    private boolean updateOrAddBan(BanService banService, Ban ban, String contenu, int user_id) throws IOException, MessagingException {
      boolean banned=false;
        if (ban == null || ban.getDate_ban() == null) {
            if (!isValidContent(contenu)) { updateBanIfNeeded(banService, ban, user_id);}
        } else {
          banned=  handleExistingBan(banService, ban, contenu);
        }
        return banned;
    }

    private void updateBanIfNeeded(BanService banService, Ban ban, int user_id) throws IOException, MessagingException {
        if (ban != null && ban.getNb_post() == 2) {
            banService.update(ban);
            banService.updateDateBan(ban);
            MailingService mailingService = new MailingService();
            mailingService.sendBannedProfileNotification("rihab.chaabane@esprit.tn");
        } else if (ban != null) {  banService.update(ban);

        } else {
            Ban ban1= new Ban();
            ban1.setUser_id(user_id);
            ban1.setNb_post(1);
            banService.add(ban1);
        }
    }

    private boolean handleExistingBan(BanService banService, Ban ban, String contenu) throws IOException {
        LocalDateTime dateBan = ban.getDate_ban().toLocalDateTime();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime dateBanPlus2Minutes = dateBan.plusMinutes(3);
        boolean banned =true;
        if (!currentDate.isBefore(dateBanPlus2Minutes)) {
            banService.delete(ban);
            banned=false;
            if (!contenu.equals(filterContent(contenu))) {
                Ban newBan = new Ban();
                newBan.setUser_id(ban.getUser_id());
                newBan.setNb_post(1);
                banService.add(newBan);

            }
        } else {
            showBannedProfileAlert();
            banned=true;
        }
        return banned;
    }

    private void showBannedProfileAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Profile banned");
        alert.setHeaderText(null);
        alert.setContentText("Your profile has been banned for 3 days due to multiple violations of our community guidelines. This action was taken because you have posted content containing inappropriate language multiple times. During this ban period, you are not allowed to post any content.");
        alert.showAndWait();
    }

    private void saveMediaFiles(int publicationId) {
        if (!selectedMediaFiles.isEmpty()) {
            MediaService mediaService = new MediaService();
            for (File file : selectedMediaFiles) {
                try {
                    byte[] fileData = Files.readAllBytes(file.toPath());
                    String mediaType = determineMediaType(file);
                    Media media = new Media(fileData, file.getAbsolutePath(), mediaType, publicationId);
                    mediaService.add(media);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(selectedMediaFiles.size() + " media files added to the database");
        }
    }

    private void redirectToProfilePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void addMedia(ActionEvent event) {
        System.out.println("Upload button clicked");
        // Open a file chooser dialog to select media files
        FileChooser fileChooser = new FileChooser();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());

        // If files are selected, add them to the list
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                String mediaType = determineMediaType(file);
                if (mediaType.equals("photo") || mediaType.equals("video")) {
                    selectedMediaFiles.add(file);
                } else {
                    // Display an error message indicating that only image or video files are allowed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Media Type");
                    alert.setHeaderText(null);
                    alert.setContentText("Only image (jpg, jpeg, png) or video (mp4, avi, mov) files are allowed.");
                    alert.showAndWait();
                }
            }
        }

    }


public String GetformattedDate(LocalDateTime dateCreation) {
    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(dateCreation, now);
    long hours = duration.toHours();

    String formattedDate;
    if (hours < 1) {
        long minutes = duration.toMinutes();
        formattedDate = String.format("%d minutes ago", minutes);
    } else if (hours < 24) {
        formattedDate = String.format("%d hours ago", hours);
    } else {
        formattedDate = String.format("%d days ago", hours / 24);
    }
return formattedDate;
}
public static String filterContent(String content) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String inputText = content;

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = String.format("api_key=b96a1ba195ec2ef8f24ab29a30ff32eb&method=webpurify.live.replace&text=%s&replacesymbol=*&lang=en&format=json", inputText);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // Construction de la requête
        Request request = new Request.Builder()
                .url("https://api1.webpurify.com/services/rest/")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String extractedText = jsonNode.get("rsp").get("text").asText();


        return extractedText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double totalHeightMainContainer = 0.0;

        publicationService = new PublicationService();

        List<Publication> publications = publicationService.getPublicationsByUserId(1);

        for (int i = publications.size() - 1; i >= 0; i--) {
            Publication publication = publications.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templatePublication.fxml"));
            try {
                Node postNode = loader.load();
                TemplatePublicationController controller = loader.getController();
                // Send the content to Bad Word Filter API
                String filteredContent = filterContent(publication.getContenu());
                controller.getCaptionpub().setText(filteredContent);
                LocalDateTime dateCreationpub = publication.getDate_creationpub().toLocalDateTime();
                controller.getDate().setText(GetformattedDate(dateCreationpub));
                controller.setId(publication.getId());

                //LIKE
                LikeService likeService = new LikeService();
                if(likeService.getLikeByIdPublicationAndIdUser(publication.getId(),1)==null)
                {controller.getImgReaction().setImage(new Image("img/disliked.png"));}
                else {
                    controller.getImgReaction().setImage(new Image("img/liked.png"));
                }
                controller.getNbReactions().setText(String.valueOf(likeService.getNumberOfLikesByIdPublication(publication.getId())));



                //MEDIAAAAA
                List<Media> medias = MediaService.getMediaListByPublicationId(publication.getId());
                controller.getMediaContainer().setAlignment(Pos.CENTER);
                for (Media media : medias) {


                    if ("photo".equals(media.getType())) {
                        LOGGER.info("Traitement du média : " + media.getType());
                        double desiredWidth = 300;
                        double desiredHeight = 220;
                        ImageView imageView = new ImageView();
//                    ByteArrayInputStream bis = new ByteArrayInputStream(media.getData());
//                    Image image = new Image(bis);
                        String imagePath = media.getChemin();

                        Image image = new Image(new File(imagePath).toURI().toString());

                        imageView.setImage(image);
                        imageView.setFitWidth(desiredWidth);
                        imageView.setFitHeight(desiredHeight);
                        imageView.setStyle("-fx-background-color: blue;");
                        setMargin(imageView, new Insets(0, 10, 0, 0));
                        imageView.setId(String.valueOf(media.getId()));
                        controller.getMediaContainer().getChildren().add(imageView);
                    } else if ("video".equals(media.getType())) {
                        // Si c'est une vidéo
                        try {
                            // Récupérer le chemin de la vidéo
                            String videoPath = media.getChemin();

                            // Créer l'objet Media à partir du chemin de la vidéo
                            javafx.scene.media.Media video = new javafx.scene.media.Media(new File(videoPath).toURI().toString());

                            // Créer le lecteur vidéo à partir de l'objet Media
                            MediaPlayer mediaPlayer = new MediaPlayer(video);

                            // Créer la vue vidéo à partir du lecteur vidéo
                            MediaView mediaView = new MediaView(mediaPlayer);

                            // Ajouter la vue vidéo au conteneur des médias
                            controller.getMediaContainer().getChildren().add(mediaView);

                            // Démarrer la lecture de la vidéo
                            mediaPlayer.setAutoPlay(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Gérer l'exception en conséquence
                        }
                    }
                }

                //COMMENTAIRE
                CommentaireService commentaireService = new CommentaireService();
                controller.getNbComments().setText(String.valueOf(commentaireService.getNumberOfCommentsByIdPublication(publication.getId())));
                List<Commentaire> commentaires = commentaireService.getCommentListByPublicationId(publication.getId());
                double totalHeight = 0.0;
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/newComment.fxml"));
                    Node postNode2 = loader2.load();
                    newCommentController controller2 = loader2.getController();
                controller2.setId(publication.getId());
                controller.getCommentContainer().getChildren().add(controller2.getnewCommentContainer());
                for (Commentaire commentaire : commentaires) {
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/Comment.fxml"));
                    try {
                        Node postNode1 = loader1.load();
                        CommentController controller1 = loader1.getController();
                        // Get the creation date and time of the comment
                        LocalDateTime dateCreation = commentaire.getDate_creation_com().toLocalDateTime();
                        controller1.getDate_com().setText(GetformattedDate(dateCreation));
                        controller1.getComment().setText(commentaire.getContenu_com());
                        controller.getCommentContainer().getChildren().add(postNode1);
                        controller1.setId(commentaire.getId());
                     //   totalHeight += postNode1.getBoundsInParent().getHeight();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

               // controller.getCommentContainer().setPrefHeight(totalHeight);
              //  System.out.println(totalHeight);
                double totalHeightPublication = postNode.getBoundsInParent().getHeight() ;
                totalHeightMainContainer += totalHeightPublication;
                mainContainer.getChildren().add(postNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
          //  mainContainer.setPrefHeight(totalHeightpub);
            mainContainer.setPrefHeight(totalHeightMainContainer);

        }


    }
}