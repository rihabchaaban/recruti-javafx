package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.*;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.LikeService;
import tn.esprit.services.MediaService;
import tn.esprit.services.CommentaireService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class comBackController {

    @FXML
    private TextArea contentTextArea;

    @FXML
    private TextField dateTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane listOfferPane;

    @FXML
    private TextField commentIdTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField postIdTextField;
    private int commentaireId;

    public void initData(CommentaireModel commentaire) {
        // Utilisez les détails de la commentaire pour initialiser les champs de votre interface utilisateur
        commentIdTextField.setText(String.valueOf(commentaire.getId()));
        postIdTextField.setText(String.valueOf(commentaire.getPublication_id()));
        userIdTextField.setText(String.valueOf(commentaire.getUser_id()));
        contentTextArea.setText(commentaire.getContenu_com());
        dateTextField.setText(String.valueOf(commentaire.getDate_creation_com()));

        this.commentaireId = commentaire.getId();
    }

    public void deleteButtonClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this comment?");

        // Add buttons to the alert dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                // Supprimer le commentaire
                CommentaireService commentaireService = new CommentaireService();
                Commentaire commentaire = new Commentaire();
                commentaire.setId(commentaireId);
                commentaireService.delete(commentaire);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception en conséquence
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListComments.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();

        }
    }
}
