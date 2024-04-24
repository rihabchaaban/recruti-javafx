package tn.esprit.controllers;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import tn.esprit.models.Commentaire;
import tn.esprit.models.Media;
import tn.esprit.models.Publication;
import tn.esprit.services.LikeService;
import tn.esprit.services.MediaService;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.PublicationService;

import java.time.Duration;
import java.time.LocalDateTime;
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
    void addPost(ActionEvent event) {
        String contenu = contenuPost.getText();

        // Create a Publication object
        Publication publication = new Publication();
        publication.setUser_id(1);
        publication.setContenu(contenu);

        PublicationService publicationService = new PublicationService();
        int publicationId = publicationService.addAndGetId(publication);

        // If media files were selected, add them to the database
        if (!selectedMediaFiles.isEmpty()) {
            // Create a MediaService instance
            MediaService mediaService = new MediaService();

            // Iterate over selected media files
            for (File file : selectedMediaFiles) {
                try {
                    // Read the file data
                    byte[] fileData = Files.readAllBytes(file.toPath());


                    // Determine media type
                    String mediaType = determineMediaType(file);

                    // Create a Media object and set its data, path, and type
                    Media media = new Media(fileData, file.getAbsolutePath(), mediaType, publicationId);


                    // Add the media to the database
                    mediaService.add(media);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle IOException (file reading error)
                }
            }

            System.out.println(selectedMediaFiles.size() + " media files added to the database");
        }

        System.out.println("Publication added successfully");

    }

    @FXML
    void addMedia(ActionEvent event) {
        System.out.println("Upload button clicked");
        // Open a file chooser dialog to select media files
        FileChooser fileChooser = new FileChooser();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());

        // If files are selected, add them to the list
        if (selectedFiles != null) {
            selectedMediaFiles.addAll(selectedFiles);
            System.out.println(selectedMediaFiles.size() + " media files selected");
        }

    }

    public void onReactionImgPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerPressed(MouseEvent mouseEvent) {
    }

    public void onLikeContainerMouseReleased(MouseEvent mouseEvent) {
    }
public String GetformattedDate(LocalDateTime dateCreation) {
    LocalDateTime now = LocalDateTime.now();
    // Calculate the difference between the two dates
    Duration duration = Duration.between(dateCreation, now);

    // Get the difference in hours
    long hours = duration.toHours();

    // Format the string based on the number of hours
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double totalHeightMainContainer = 0.0;

        publicationService = new PublicationService();

        List<Publication> publications = publicationService.getPublicationsByUserId(1);

        for (Publication publication : publications) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templatePublication.fxml"));
            try {
                Node postNode = loader.load();
                TemplatePublicationController controller = loader.getController();
                LocalDateTime dateCreationpub = publication.getDate_creationpub().toLocalDateTime();
                controller.getDate().setText(GetformattedDate(dateCreationpub));
                controller.getCaptionpub().setText(publication.getContenu());
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
                        double desiredWidth = 300; // Remplacez par la largeur souhaitée
                        double desiredHeight = 220; // Remplacez par la hauteur souhaitée

                        ImageView imageView = new ImageView();
//                    ByteArrayInputStream bis = new ByteArrayInputStream(media.getData());
//                    Image image = new Image(bis);
                        String imagePath = media.getChemin(); // Chemin de l'image

                        // Créer l'objet Image à partir du chemin de l'image
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