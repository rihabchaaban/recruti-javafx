package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.*;
import services.CommentaireService;
import services.LikeService;
import services.MediaService;
import services.PublicationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class pubBackController {

    @FXML
    private Button deleteButton;

    @FXML
    private TextField dateTextField;

    @FXML
    private AnchorPane listOfferPane;

    @FXML
    private TextField postIdTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextArea contentTextArea;
    private int publicationId;


    public void initData(PublicationModel publication) {
        postIdTextField.setText(String.valueOf(publication.getId()));
        userIdTextField.setText(String.valueOf(publication.getUserId()));
        contentTextArea.setText(publication.getContenu());
        dateTextField.setText(String.valueOf(publication.getDateCreationpub()));

        this.publicationId = publication.getId();
    }

    public void deleteButtonClicked(MouseEvent mouseEvent) throws IOException {
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
                List<Commentaire> commentaires = CommentaireService.getCommentListByPublicationId(publicationId);
                for (Commentaire commentaire : commentaires) {
                    CommentaireService commentaireService = new CommentaireService();
                    commentaireService.delete(commentaire);
                }

                // Supprimer les médias associés à la publication
                List<Media> mediaList = MediaService.getMediaListByPublicationId(publicationId);
                for (Media media : mediaList) {
                    MediaService mediaService = new MediaService();
                    mediaService.delete(String.valueOf(media.getId()));
                }
                // Supprimer les likes associés à la publication
                List<Like> likeList = LikeService.getLikeListByPublicationId(publicationId);
                for (Like like : likeList) {
                    LikeService likeService = new LikeService();
                    likeService.delete(like);
                }

                // Supprimer la publication
                PublicationService publicationService = new PublicationService();
                Publication publication = new Publication();
                publication.setId(publicationId);
                publicationService.delete(publication);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception en conséquence
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPublications.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();

        }
    }
}

