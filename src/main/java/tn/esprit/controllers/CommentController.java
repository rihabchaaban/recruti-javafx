package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Media;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.MediaService;
import tn.esprit.services.PublicationService;

import java.util.List;
import java.util.Optional;

public class CommentController {

    @FXML
    private TextArea comment;

    @FXML
    private ImageView delete;

    @FXML
    private ImageView edit;

    @FXML
    private ImageView imgProfile;

    @FXML
    private VBox pubContainer;

    @FXML
    private Label username;
    @FXML
    private Label date_com;
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private boolean isEditing = false;

    @FXML
    void onEditClicked(MouseEvent event) {
        if (!isEditing) {
            comment.setEditable(true);
            edit.setImage(new Image("img/save.png"));

        } else {

            comment.setEditable(false);
            String newCaption = comment.getText();
            CommentaireService commentaireService = new CommentaireService();
            Commentaire commentaire = new Commentaire();
            commentaire.setId(id);
            commentaire.setContenu_com(newCaption);
            commentaireService.update(commentaire);
            // Restaurer l'icône d'édition
            edit.setImage(new Image("img/edit.png"));

        }
        isEditing = !isEditing;

    }

    public Label getDate_com() {
        return date_com;
    }

    public void setDate_com(Label date_com) {
        this.date_com = date_com;
    }

    public TextArea getComment() {
        return comment;
    }

    public void setComment(TextArea comment) {
        this.comment = comment;
    }

    public ImageView getDelete() {
        return delete;
    }

    public void setDelete(ImageView delete) {
        this.delete = delete;
    }

    public ImageView getEdit() {
        return edit;
    }

    public void setEdit(ImageView edit) {
        this.edit = edit;
    }

    public ImageView getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(ImageView imgProfile) {
        this.imgProfile = imgProfile;
    }

    public VBox getPubContainer() {
        return pubContainer;
    }

    public void setPubContainer(VBox pubContainer) {
        this.pubContainer = pubContainer;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
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

                CommentaireService commentaireService = new CommentaireService();
                Commentaire commentaire = new Commentaire();
                commentaire.setId(id);
                System.out.println(id);
                commentaireService.delete(commentaire);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception en conséquence
            }
        }

    }
}

