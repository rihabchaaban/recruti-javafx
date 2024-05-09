package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Like;
import models.LikeModel;
import services.LikeService;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class likeBackController {
    @FXML
    private TextField likeIdTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane listOfferPane;

    @FXML
    private TextField postIdTextField;

    @FXML
    private TextField userIdTextField;
    private int likeId;

    public void initData(LikeModel like) {
        // Utilisez les détails de la like pour initialiser les champs de votre interface utilisateur
        likeIdTextField.setText(String.valueOf(like.getId()));
        postIdTextField.setText(String.valueOf(like.getPublication_id()));
        userIdTextField.setText(String.valueOf(like.getUser_Id()));
        dateTextField.setText(String.valueOf(like.getDate_creation_like()));

        this.likeId = like.getId();
    }

    @FXML
    void deleteButtonClicked(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this like?");

        // Add buttons to the alert dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                // Supprimer le like
                LikeService likeService = new LikeService();
                Like like = new Like();
                like.setId(likeId);
                likeService.delete(like);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'exception en conséquence
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListLikes.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.show();

        }

    }

}
