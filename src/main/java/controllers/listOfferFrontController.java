package controllers;

import javafx.scene.Node;
import models.Offer;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listOfferFrontController implements Initializable {

    @FXML
    private ImageView background;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listOfferFront;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;

    @FXML
    private TextField searchBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refrech();

        try {
            OfferService os = new OfferService();
            FilteredList<Offer> filteredData = new FilteredList<>(FXCollections.observableArrayList(os.afficher()), p -> true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(act -> {
                    System.out.println(newValue);
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String titre = String.valueOf(act.getTitre_o());
                    String type = String.valueOf(act.getType_o());
                    String localisation = String.valueOf(act.getLocalisation_o());
                    String duree = String.valueOf(act.getDure_o());
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (titre.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (type.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (localisation.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (duree.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false;
                    }
                });
                SortedList<Offer> sortedData = new SortedList<>(filteredData);

                pag.setPageCount((int) Math.ceil(sortedData.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
                pag.setPageFactory(pageIndex -> {
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    int itemsPerPage = 3; // Nombre des sujets à afficher par page
                    int page = pageIndex * itemsPerPage;
                    for (int i = page; i < Math.min(page + itemsPerPage, sortedData.size()); i++) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/listOfferFrontCard.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();
                            anchorPane.getStyleClass().add("ct");
                            listOfferFrontCardController itemController = fxmlLoader.getController();
                            itemController.setData(sortedData.get(i));
                            hbox.getChildren().add(anchorPane);
                            HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                        } catch (IOException ex) {
                            Logger.getLogger(listOfferFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    return hbox;
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void open_viewCondidature(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/viewCondidatureCandidat.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Offer");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
        refrech();
    }

    public void refrech(){
        try {
            OfferService os = new OfferService();
            FilteredList<Offer> filteredData = new FilteredList<>(FXCollections.observableArrayList(os.afficher()), p -> true);


                pag.setPageCount((int) Math.ceil(filteredData.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
                pag.setPageFactory(pageIndex -> {
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    int itemsPerPage = 3; // Nombre des sujets à afficher par page
                    int page = pageIndex * itemsPerPage;
                    for (int i = page; i < Math.min(page + itemsPerPage, filteredData.size()); i++) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/listOfferFrontCard.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();
                            anchorPane.getStyleClass().add("ct");
                            listOfferFrontCardController itemController = fxmlLoader.getController();
                            itemController.setData(filteredData.get(i));
                            hbox.getChildren().add(anchorPane);
                            HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                        } catch (IOException ex) {
                            Logger.getLogger(listOfferFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    return hbox;
                });
        } catch (Exception e) {
            e.printStackTrace();
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
