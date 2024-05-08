package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.*;
import tn.esprit.models.Like;
import tn.esprit.models.LikeModel;
import tn.esprit.services.LikeService;
import tn.esprit.services.LikeService;
import tn.esprit.services.PublicationService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ListLikes implements Initializable {
    @FXML
    private TableView<LikeModel> likeTableView;
    @FXML
    private DatePicker searchDateField;

    @FXML
    private TextField searchIdField;

    @FXML
    private TextField searchIdPostField;

    @FXML
    private TextField searchIdUserField;

    @FXML
    private AnchorPane listOfferPane;
    @FXML
    private VBox topLikedPostsVBox;
    private LikeService likeService;
    private PublicationService publicationService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        likeService = new LikeService();
        loadLikes();
        publicationService = new PublicationService();
        loadTopLikedPosts();
    }


    public void loadLikes() {

        List<Like> likes = likeService.getAll();
        List<LikeModel> likeModels = likes.stream()
                .map(like -> {
                    return new LikeModel(
                            like.getId(),
                            like.getUser_id(),
                            like.getPublication_id(),
                            like.getDate_creation_like()
                    );
                })
                .collect(Collectors.toList());

        likeTableView.getItems().clear();
        likeTableView.getItems().addAll(likeModels);

        TableColumn<LikeModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<LikeModel, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().user_IdProperty().asObject());

        TableColumn<LikeModel, Integer> publicationColumn = new TableColumn<>("Publication ID");
        publicationColumn.setCellValueFactory(cellData -> cellData.getValue().publication_idProperty().asObject());


        TableColumn<LikeModel, Timestamp> dateColumn = new TableColumn<>("Date of creation");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().date_creation_likeProperty());

        likeTableView.getColumns().setAll(idColumn, userIdColumn, publicationColumn, dateColumn);

        TableColumn<LikeModel, Void> viewButtonColumn = new TableColumn<>("View");
        viewButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View");

            {
                viewButton.setOnAction(event -> {
                    LikeModel like = getTableView().getItems().get(getIndex());
                    // Appeler une méthode pour afficher les détails de la like
                    try {
                        // Charger le fichier FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/likeBack.fxml"));
                        AnchorPane root = loader.load();

                        // Passer les détails de la like au contrôleur de la nouvelle page
                        likeBackController controller = loader.getController();
                        controller.initData(like);

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
        likeTableView.getColumns().add(viewButtonColumn);
    }
    @FXML
    public void handleSearchButton(javafx.event.ActionEvent event) {

        String searchText = searchIdField.getText();
        String searchTextuser = searchIdUserField.getText();
        LocalDate searchDate = searchDateField.getValue();
        String searchTextpost = searchIdPostField.getText();

        List<Like> filteredLikes = likeService.getAll().stream()
                .filter(like -> {
                    boolean matchesId = true;
                    boolean matchesIduser = true;
                    boolean matchesDate = true;
                    boolean matchesIdpost = true;

                    if (!searchText.isEmpty()) {
                        matchesId = like.getId() == Integer.parseInt(searchText);
                    }
                    if (! searchTextuser.isEmpty()) {
                        matchesIduser = like.getUser_id() == Integer.parseInt(searchTextuser);
                    }
                    if (! searchTextpost.isEmpty()) {
                        matchesIdpost = like.getPublication_id() == Integer.parseInt(searchTextpost);
                    }
                    if (searchDate != null) {
                        matchesDate = like.getDate_creation_like().toLocalDateTime().toLocalDate().equals(searchDate);
                    }

                    return matchesId && matchesDate &&  matchesIduser && matchesIdpost;
                })
                .collect(Collectors.toList());

        // Update the table view with filtered comments
        updateTableView(filteredLikes);
    }

    private void updateTableView(List<Like> likes) {
        List<LikeModel> likeModels = likes.stream()
                .map(like -> {
                    return new LikeModel(
                            like.getId(),
                            like.getUser_id(),
                            like.getPublication_id(),
                            like.getDate_creation_like()
                    );
                })
                .collect(Collectors.toList());

        likeTableView.getItems().clear();
        likeTableView.getItems().addAll(likeModels);
    }
    public void loadTopLikedPosts() {

        List<Integer> Mostlikedpost = likeService. getTop3LikedPublicationIds();
        // Récupérer les publications les plus aimées depuis le service
        List<Publication> topLikedPublications = publicationService.getPublicationsByIds(Mostlikedpost);


        // Effacer les enfants actuels du VBox
        topLikedPostsVBox.getChildren().clear();

        for (int i = topLikedPublications.size() - 1; i >= 0; i--) {
            Publication publication = topLikedPublications.get(i);
            try {
                // Charger le fichier FXML de la carte de publication
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/postback.fxml"));
                AnchorPane postCard = loader.load();

                // Passer les données de la publication à la carte de publication
                PostbackController controller = loader.getController();
                controller.initData(publication);

                // Ajouter la carte de publication au VBox
                topLikedPostsVBox.getChildren().add(postCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
