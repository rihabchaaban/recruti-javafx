package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.PublicationService;

public class newCommentController {

    @FXML
    private ImageView ajoutComment;

    @FXML
    private TextArea comment;

    @FXML
    private ImageView imgProfile;

    @FXML
    private VBox newCommentContainer;

    @FXML
    private Label username;
    private int id ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void onAddClicked(MouseEvent event) {
        String contenu = comment.getText();
        Commentaire commentaire = new Commentaire();
        commentaire.setUser_id(1);
        commentaire.setPublication_id(id);
        commentaire.setContenu_com(contenu);
        CommentaireService commentaireService = new CommentaireService();
        commentaireService.add(commentaire);
        System.out.println("Commentaire added successfully");

    }

    public ImageView getAjoutComment() {
        return ajoutComment;
    }

    public void setAjoutComment(ImageView ajoutComment) {
        this.ajoutComment = ajoutComment;
    }

    public TextArea getComment() {
        return comment;
    }

    public void setComment(TextArea comment) {
        this.comment = comment;
    }

    public ImageView getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(ImageView imgProfile) {
        this.imgProfile = imgProfile;
    }

    public VBox getnewCommentContainer() {
        return newCommentContainer;
    }

    public void setnewCommentContainer(VBox newCommentContainer) {
        this.newCommentContainer = newCommentContainer;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }
}