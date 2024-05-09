package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Commentaire;
import models.Like;
import models.Media;
import models.Publication;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TemplatePublicationController {

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    private int id ;


    @FXML
    private Label date;

    @FXML
    private ImageView imgAngry;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgPost;

    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgReaction;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgVerified;

    @FXML
    private ImageView imgWow;

    @FXML
    private HBox likeContainer;
    @FXML
    private VBox CommentContainer;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbShares;

    @FXML
    private VBox pubContainer;

    @FXML
    private Label reactionName;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private Label username;
    @FXML
    private HBox mediaContainer;
    @FXML
    private ImageView delete;
    @FXML
    private ImageView edit;
    @FXML
    private TextArea captionpub;
    private boolean isEditing = false;
    private boolean isLiked = false;
    private long startTime = 0;

    public HBox getMediaContainer() {
        return mediaContainer;
    }

    public void setMediaContainer(HBox mediaContainer) {
        this.mediaContainer = mediaContainer;
    }


    @FXML
    public void onLikeContainerPressed(MouseEvent me){
        startTime = System.currentTimeMillis();
    }

    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me){

    }

    @FXML
    public void onReactionImgPressed(MouseEvent me){

    }




    public ImageView getAudience() {
        return audience;
    }

    public Label getCaption() {
        return caption;
    }

    public Label getDate() {
        return date;
    }

    public ImageView getImgAngry() {
        return imgAngry;
    }

    public ImageView getImgCare() {
        return imgCare;
    }

    public ImageView getImgHaha() {
        return imgHaha;
    }

    public ImageView getImgLike() {
        return imgLike;
    }

    public ImageView getImgLove() {
        return imgLove;
    }

    public ImageView getImgPost() {
        return imgPost;
    }

    public ImageView getImgProfile() {
        return imgProfile;
    }

    public ImageView getImgReaction() {
        return imgReaction;
    }

    public ImageView getImgSad() {
        return imgSad;
    }

    public ImageView getImgVerified() {
        return imgVerified;
    }

    public ImageView getImgWow() {
        return imgWow;
    }

    public HBox getLikeContainer() {
        return likeContainer;
    }

    public Label getNbComments() {
        return nbComments;
    }

    public Label getNbReactions() {
        return nbReactions;
    }

    public Label getNbShares() {
        return nbShares;
    }

    public VBox getPubContainer() {
        return pubContainer;
    }

    public Label getReactionName() {
        return reactionName;
    }

    public HBox getReactionsContainer() {
        return reactionsContainer;
    }

    public Label getUsername() {
        return username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


public void onEditClicked(MouseEvent mouseEvent) {
    if (!isEditing) {
        captionpub.setEditable(true);
        edit.setImage(new Image("image/save.png"));
        addDeleteIconToMedia();

    } else {

        captionpub.setEditable(false);
        String newCaption = captionpub.getText();
        PublicationService publicationService = new PublicationService();
        Publication publication = new Publication();
        publication.setId(id);
        publication.setContenu(newCaption);
        publicationService.update(publication);
        // Supprimer l'icône de suppression de chaque image
        removeDeleteIconsFromMedia();
        // Restaurer l'icône d'édition
        edit.setImage(new Image("image/edit (2).png"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException (FXML loading error)
        }

    }
    isEditing = !isEditing;
}
    @FXML
    void onLikeContainerClicked(MouseEvent event) {
        LikeService likeService = new LikeService();
        Like like = new Like();
            if(likeService.getLikeByIdPublicationAndIdUser(id,userService.getUser().getId())==null){
            like.setUser_id(userService.getUser().getId());
            like.setPublication_id(id);
            likeService.add(like);
            imgReaction.setImage(new Image("image/liked.png"));
        } else {
            like=likeService.getLikeByIdPublicationAndIdUser(id,userService.getUser().getId());
            likeService.delete(like);
            // Restaurer l'icône d'édition
            imgReaction.setImage(new Image("image/disliked.png"));
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException (FXML loading error)
        }


    }

private String getImagePath(ImageView imageView) {
    Image image = imageView.getImage();
    if (image != null && image.getUrl() != null) {
        return image.getUrl();
    }
    return null;
}
public void removeDeleteIconsFromMedia() {
    // Boucler sur les enfants de la HBox mediaContainer
    for (Node node : mediaContainer.getChildren()) {
        if (node instanceof StackPane) {
            StackPane stackPane = (StackPane) node;
            // Supprimer l'icône de suppression de chaque StackPane
            Node deleteIcon = null;
            for (Node child : stackPane.getChildren()) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    String imagePath = getImagePath(imageView);
                    System.out.println(imagePath);
                    if (imagePath != null && imagePath.equals("file:/C:/Users/Rihab/Desktop/git%20java/recruti-javafx/target/classes/img/deleter.png")) {
                        deleteIcon = child;
                        break; // Sortir de la boucle dès qu'une icône est trouvée*

                    }
                }

            }
            // Supprimer l'icône de suppression du StackPane
            if (deleteIcon != null) {
                stackPane.getChildren().remove(deleteIcon);
            }
        }
    }
}


    public void addDeleteIconToMedia() {
    // Copier la liste des enfants de mediaContainer pour éviter ConcurrentModificationException
    List<Node> mediaNodes = new ArrayList<>(mediaContainer.getChildren());
        System.out.println( mediaContainer.getChildren().toString());


    // Itérer sur la copie de la liste des enfants
    for (Node mediaNode : mediaNodes) {
        if (mediaNode instanceof ImageView) {

            ImageView deleteIcon = new ImageView(new Image("image/deleter.png"));
            deleteIcon.setFitWidth(20);
            deleteIcon.setFitHeight(20);
            // Créer un StackPane pour contenir l'image et l'icône de suppression
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll((ImageView) mediaNode, deleteIcon);
            StackPane.setAlignment(deleteIcon, Pos.TOP_RIGHT);

            // Remplacer l'image par le StackPane contenant l'image et l'icône de suppression
            mediaContainer.getChildren().remove(mediaNode);
            mediaContainer.getChildren().add(stackPane);

            deleteIcon.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this media from your publication?");

                // Add buttons to the alert dialog
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                // Wait for user's response
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeYes) {
                    mediaContainer.getChildren().remove(stackPane);
                    MediaService mediaService = new MediaService();
                    mediaService.delete(mediaNode.getId());


                }
                });
        }
    }
}

    public VBox getCommentContainer() {
        return CommentContainer;
    }

    public void setCommentContainer(VBox commentContainer) {
        CommentContainer = commentContainer;
    }

    public TextArea getCaptionpub() {
        return captionpub;
    }

    public void setCaptionpub(TextArea captionpub) {
        this.captionpub = captionpub;
    }

    private boolean commentContainerVisible = false;
    @FXML
    void OnCommentClicked(MouseEvent event) {
        // Inverser l'état de visibilité
        commentContainerVisible = !commentContainerVisible;

        // Modifier la visibilité du CommentContainer en conséquence
        if (commentContainerVisible) {
            // Afficher le CommentContainer
            CommentContainer.setVisible(true);
          //  CommentContainer.setPrefHeight(CommentContainer.getPrefHeight());
        } else {
            // Masquer le CommentContainer
            CommentContainer.setVisible(false);
        }
    }
    @FXML
    void onDeleteClicked(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this post?");

        // Add buttons to the alert dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                // Supprimer les commentaires associés à la publication
                List<Commentaire> commentaires = CommentaireService.getCommentListByPublicationId(id);
                for (Commentaire commentaire : commentaires) {
                    CommentaireService commentaireService = new CommentaireService();
                    commentaireService.delete(commentaire);
                }

                // Supprimer les médias associés à la publication
                List<Media> mediaList = MediaService.getMediaListByPublicationId(id);
                for (Media media : mediaList) {
                    MediaService mediaService = new MediaService();
                    mediaService.delete(String.valueOf(media.getId()));
                }
                // Supprimer les likes associés à la publication
                List<Like> likeList = LikeService.getLikeListByPublicationId(id);
                for (Like like : likeList) {
                    LikeService likeService = new LikeService();
                    likeService.delete(like);
                }

                // Supprimer la publication
                PublicationService publicationService = new PublicationService();
                Publication publication = new Publication();
                publication.setId(id);
                publicationService.delete(publication);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception en conséquence
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle IOException (FXML loading error)
            }
        }
    }
  //  public void initialize(URL url, ResourceBundle resourceBundle) { username.setText(userService.getUser().getUsername());}
}
