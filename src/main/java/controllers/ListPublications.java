package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Publication;
import models.PublicationModel;
import services.PublicationService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListPublications implements Initializable {

    @FXML
    private DatePicker searchDateField;
    @FXML
    private TextField searchIdUserField;

    @FXML
    private TextField searchIdField;
    @FXML
    private AnchorPane listOfferPane;
    @FXML
    private ListView<Publication> publicationListView;
    @FXML
    private TableView<PublicationModel> publicationTableView;
    @FXML
    private Button comment;

    @FXML
    private Button like;
    @FXML
    private VBox topLikedPostsVBox;
    private PublicationService publicationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publicationService = new PublicationService();
        loadPublications();
    }
    @FXML
    void goToPages(ActionEvent event) throws IOException {
        if(event.getSource() == comment){
            Parent fxml = FXMLLoader.load(getClass().getResource("/ListComments.fxml"));
            listOfferPane.getChildren().removeAll();
            listOfferPane.getChildren().setAll(fxml);
        } else if(event.getSource() == like){
            Parent fxml = FXMLLoader.load(getClass().getResource("/listLikes.fxml"));
            listOfferPane.getChildren().removeAll();
            listOfferPane.getChildren().setAll(fxml);
        }
    }


public void loadPublications() {

    List<Publication> publications = publicationService.getAll();
    List<PublicationModel> publicationModels = publications.stream()
            .map(publication -> {
                try {
                    return new PublicationModel(
                            publication.getId(),
                            publication.getUser_id(),
                            Profile.filterContent(publication.getContenu()),
                            publication.getDate_creationpub()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());

    publicationTableView.getItems().clear();
    publicationTableView.getItems().addAll(publicationModels);

    TableColumn<PublicationModel, Integer> idColumn = new TableColumn<>("ID");
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

    TableColumn<PublicationModel, Integer> userIdColumn = new TableColumn<>("User ID");
    userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());

    TableColumn<PublicationModel, String> contenuColumn = new TableColumn<>("Content");
    contenuColumn.setCellValueFactory(cellData -> cellData.getValue().contenuProperty());

    TableColumn<PublicationModel, Timestamp> dateColumn = new TableColumn<>("Date of creation");
    dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateCreationpubProperty());

    publicationTableView.getColumns().setAll(idColumn, userIdColumn, contenuColumn, dateColumn);

    TableColumn<PublicationModel, Void> viewButtonColumn = new TableColumn<>("View");
    viewButtonColumn.setCellFactory(param -> new TableCell<>() {
        private final Button viewButton = new Button("View");

        {
            viewButton.setOnAction(event -> {
                PublicationModel publication = getTableView().getItems().get(getIndex());
                // Appeler une méthode pour afficher les détails de la publication
                try {
                    // Charger le fichier FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pubBack.fxml"));
                    AnchorPane root = loader.load();

                    // Passer les détails de la publication au contrôleur de la nouvelle page
                    pubBackController controller = loader.getController();
                    controller.initData(publication);

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


    publicationTableView.getColumns().add(viewButtonColumn);
}
    @FXML
   public void handleSearchButton(javafx.event.ActionEvent event) {

        String searchText = searchIdField.getText();
        String searchTextuser = searchIdUserField.getText();
        LocalDate searchDate = searchDateField.getValue();

        List<Publication> filteredPublications = publicationService.getAll().stream()
                .filter(publication -> {
                    boolean matchesId = true;
                    boolean matchesIduser = true;
                    boolean matchesDate = true;


                    if (!searchText.isEmpty()) {
                        matchesId = publication.getId() == Integer.parseInt(searchText);
                    }
                    if (! searchTextuser.isEmpty()) {
                        matchesIduser = publication.getUser_id() == Integer.parseInt(searchTextuser);
                    }
                    if (searchDate != null) {
                        matchesDate = publication.getDate_creationpub().toLocalDateTime().toLocalDate().equals(searchDate);
                    }

                    return matchesId && matchesDate &&  matchesIduser;
                })
                .collect(Collectors.toList());

        // Update the table view with filtered publications
        updateTableView(filteredPublications);
    }

    private void updateTableView(List<Publication> publications) {
        List<PublicationModel> publicationModels = publications.stream()
                .map(publication -> {
                    try {
                        return new PublicationModel(
                                publication.getId(),
                                publication.getUser_id(),
                                Profile.filterContent(publication.getContenu()),
                                publication.getDate_creationpub()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        publicationTableView.getItems().clear();
        publicationTableView.getItems().addAll(publicationModels);
    }




}
