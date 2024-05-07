package gui;

import entities.Biblio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.BiblioService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class listBiblioController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listBiblioPane;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;

    @FXML
    private Label totalLibrariesLabel;

    @FXML
    private TextField searchField;

    private ObservableList<Biblio> bibliosData = FXCollections.observableArrayList();

    public int getTotalBiblios() {
        try {
            BiblioService bs = new BiblioService();
            List<Biblio> biblios = bs.afficher();
            bibliosData.addAll(biblios);
            return biblios.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Retourne 0 en cas d'erreur
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Récupérer le nombre total de bibliothèques disponibles
            int totalBiblios = getTotalBiblios();

            // Afficher le nombre total de bibliothèques dans l'étiquette
            totalLibrariesLabel.setText("Total Libraries: " + totalBiblios);

            pag.setPageCount((int) Math.ceil(bibliosData.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, bibliosData.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("itemBiblio.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        itemBiblioController itemController = fxmlLoader.getController();
                        itemController.setData(bibliosData.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(itemBiblioController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        if (searchTerm.isEmpty()) {
            resetPagination();
        } else {
            List<Biblio> searchResult = bibliosData.stream()
                    .filter(biblio -> biblio.getNom_b().toLowerCase().contains(searchTerm) ||
                            biblio.getDomaine_b().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
            pag.setPageCount((int) Math.ceil(searchResult.size() / 3.0));
            // Mise à jour de l'affichage avec les résultats de la recherche
            updatePagination(searchResult);
        }
    }

    private void resetPagination() {
        pag.setPageCount((int) Math.ceil(bibliosData.size() / 3.0));
        updatePagination(bibliosData);
    }

    private void updatePagination(List<Biblio> data) {
        pag.setPageFactory(pageIndex -> {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            int itemsPerPage = 3;
            int page = pageIndex * itemsPerPage;
            for (int i = page; i < Math.min(page + itemsPerPage, data.size()); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("itemBiblio.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    anchorPane.getStyleClass().add("ct");
                    itemBiblioController itemController = fxmlLoader.getController();
                    itemController.setData(data.get(i));
                    hbox.getChildren().add(anchorPane);
                    HBox.setMargin(anchorPane, new Insets(10));
                } catch (IOException ex) {
                    Logger.getLogger(itemBiblioController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            return hbox;
        });
    }
}
