package controllers;

import javafx.scene.Node;
import models.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.EventService;
import services.ParticipationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listEventFrontController implements Initializable {
    @FXML
    private ImageView background;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listEventFront;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;

    @FXML
    private TextField SearchEventId;

    @FXML
    private ComboBox<String> combosortEvent;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sortingOptions = FXCollections.observableArrayList(
                "date", "nom", "lieu","test"
        );
        combosortEvent.setItems(sortingOptions);
        refrech(); }

    @FXML
    void open_addEvent(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/addEvent.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Event");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
        refrech();

    }

    private void refrech() {
        try {
            EventService os = new EventService();
            List<Event> events = os.getAll();
            pag.setPageCount((int) Math.ceil(events.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, events.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/listEventFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listEventFrontCardController itemController = fxmlLoader.getController();
                        itemController.setData(events.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listEventFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateVBoxEvents(List<Event> events) {
        try {


            pag.setPageCount((int) Math.ceil(events.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, events.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/listEventFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listEventFrontCardController itemController = fxmlLoader.getController();
                        itemController.setData(events.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listEventFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void SearchEvent(ActionEvent event) {
        try {
            EventService eventService = new EventService(); // Créer une instance de EventService
            String searchCriteria = SearchEventId.getText();
            if (searchCriteria.isEmpty()) {
                // Si le critère de recherche est vide, réinitialiser l'affichage pour afficher toutes les données
                List<Event> events = eventService.getAll();
                updateVBoxEvents(events);
            } else {
                // Sinon, effectuer la recherche
                List<Event> events = eventService.searchEvents(searchCriteria);
                updateVBoxEvents(events); // Mettre à jour l'interface utilisateur avec les résultats de la recherche
                // Réinitialiser la pagination

            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'exception SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void sortTableView(ActionEvent event) {
        try {
            EventService eventService = new EventService();
            String selectedSortOption = combosortEvent.getValue();
            List<Event> sortedEvents = null;

            switch (selectedSortOption) {
                case "date":
                    sortedEvents = eventService.displaySorted("date_e");
                    break;
                case "nom":
                    sortedEvents = eventService.displaySorted("nom_e");
                    break;
                case "lieu":
                    sortedEvents = eventService.displaySorted("lieu_e");
                    break;
                default:
                    sortedEvents = eventService.getAll();
                    break;
            }

            updateVBoxEvents(sortedEvents);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void viewCharts(ActionEvent event) {
        try {
            ParticipationService participationService = new ParticipationService();
            EventService eventService = new EventService();

            // Obtenez le nombre de participations par événement
            Map<Integer, Long> participationCountPerEvent = participationService.getParticipationCountPerEvent();

            // Créez une liste de segments de données pour le PieChart des participations par événement
            ObservableList<PieChart.Data> participationChartData = FXCollections.observableArrayList();
            participationCountPerEvent.forEach((eventId, count) -> {
                try {
                    Event eventObj = eventService.getById(eventId);
                    participationChartData.add(new PieChart.Data(eventObj.getNom_e(), count));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            // Créez le PieChart pour les participations par événement
            PieChart participationPieChart = new PieChart(participationChartData);
            participationPieChart.setTitle("Nombre de participations par événement");
            participationPieChart.setPrefSize(400, 400); // Réglez la taille du PieChart ici

            // Obtenez la répartition des événements par type
            Map<String, Long> eventTypeDistribution = eventService.getEventTypeDistribution();

            // Créez une liste de segments de données pour le PieChart des événements par thème
            ObservableList<PieChart.Data> eventChartData = FXCollections.observableArrayList();
            eventTypeDistribution.forEach((eventType, count) -> {
                eventChartData.add(new PieChart.Data(eventType, count));
            });

            // Créez le PieChart pour la répartition des événements par thème
            PieChart eventPieChart = new PieChart(eventChartData);
            eventPieChart.setTitle("Répartition des événements par thème");
            eventPieChart.setPrefSize(400, 400); // Réglez la taille du PieChart ici

            // Créez un conteneur pour afficher les deux graphiques
            HBox chartsContainer = new HBox();
            chartsContainer.getChildren().addAll(participationPieChart, eventPieChart);
            chartsContainer.setSpacing(20); // Réglez l'espacement entre les deux graphiques

            // Créez un dialogue pour afficher les deux graphiques
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Graphiques");
            dialog.setHeaderText("Graphiques de données");
            dialog.getDialogPane().setContent(chartsContainer);

            // Ajoutez un bouton OK pour fermer le dialogue
            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(okButtonType);

            // Affichez le dialogue et attendez la réponse de l'utilisateur
            dialog.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la récupération des données: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void backhome(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/HelloFrontR.fxml"));

        // Créer une nouvelle scène et un nouveau stage pour afficher la nouvelle page
        Stage newStage = new Stage();
        newStage.setTitle("Recruti");
        newStage.setScene(new Scene(fxml));

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Afficher la nouvelle fenêtre
        newStage.show();

    }




}



