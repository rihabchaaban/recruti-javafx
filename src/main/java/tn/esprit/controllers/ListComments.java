package tn.esprit.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Commentaire;
import tn.esprit.models.CommentaireModel;
import tn.esprit.models.Commentaire;
import tn.esprit.models.CommentaireModel;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.CommentaireService;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListComments implements Initializable {
    @FXML
    private AnchorPane listOfferPane;

    @FXML
    private TableView<CommentaireModel> commentaireTableView;
    private CommentaireService commentaireService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commentaireService = new CommentaireService();
        loadComments();
        // Centrer le TableView dans l'AnchorPane parent
//        AnchorPane.setTopAnchor(commentaireTableView, (listOfferPane.getHeight() - commentaireTableView.getHeight()) / 2);
//        AnchorPane.setLeftAnchor(commentaireTableView, (listOfferPane.getWidth() - commentaireTableView.getWidth()) / 2);
//
    }


    public void loadComments() {

        List<Commentaire> commentaires = commentaireService.getAll();
        List<CommentaireModel> commentaireModels = commentaires.stream()
                .map(commentaire -> {
                    try {
                        return new CommentaireModel(
                                commentaire.getId(),
                                commentaire.getUser_id(),
                                commentaire.getPublication_id(),
                                Profile.filterContent(commentaire.getContenu_com()),
                                commentaire.getDate_creation_com()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        commentaireTableView.getItems().clear();
        commentaireTableView.getItems().addAll(commentaireModels);

        TableColumn<CommentaireModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<CommentaireModel, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().user_idProperty().asObject());

        TableColumn<CommentaireModel, Integer> publicationIdColumn = new TableColumn<>("Publication ID");
        publicationIdColumn.setCellValueFactory(cellData -> cellData.getValue(). publication_idProperty().asObject());

        TableColumn<CommentaireModel, String> contenuColumn = new TableColumn<>("Content");
        contenuColumn.setCellValueFactory(cellData -> cellData.getValue().contenu_comProperty());

        TableColumn<CommentaireModel, Timestamp> dateColumn = new TableColumn<>("Date of creation");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date_creation_comProperty());

        commentaireTableView.getColumns().setAll(idColumn, userIdColumn,publicationIdColumn, contenuColumn, dateColumn);

        TableColumn<CommentaireModel, Void> viewButtonColumn = new TableColumn<>("View");
        viewButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View");

            {
                viewButton.setOnAction(event -> {
                    CommentaireModel commentaire = getTableView().getItems().get(getIndex());
                    // Appeler une méthode pour afficher les détails de la commentaire
                    try {
                        // Charger le fichier FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/comBack.fxml"));
                        AnchorPane root = loader.load();

                        // Passer les détails de la commentaire au contrôleur de la nouvelle page
                        comBackController controller = loader.getController();
                        controller.initData(commentaire);

                        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage1.close();
                        // Afficher la nouvelle page dans une nouvelle fenêtre ou dans la même fenêtre, selon votre besoin
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });

        // Ajouter la colonne de bouton "View" à la table
        commentaireTableView.getColumns().add(viewButtonColumn);
    }



}
