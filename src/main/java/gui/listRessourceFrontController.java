package gui;

import entities.Ressource;
import gui.itemRessourceController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.RessourceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class listRessourceFrontController implements Initializable {

    @FXML
    private AnchorPane listRessourceFrontPane;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hBox;

    @FXML
    private HBox vbox;

    @FXML
    private Pagination pag;
    @FXML
    private Label totalRessourcesLabel;

    @FXML
    private TextField searchFieldTitle;

    @FXML
    private TextField searchFieldType;

    @FXML
    private TextField searchFieldField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            RessourceService rs = new RessourceService();
            List<Ressource> res = rs.afficher();
            pag.setPageCount((int) Math.ceil(res.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, res.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("listRessourceFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listRessourceFrontCardController itemController = fxmlLoader.getController();
                        itemController.setData(res.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listRessourceFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
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
        String title = searchFieldTitle.getText().toLowerCase().trim();
        String type = searchFieldType.getText().toLowerCase().trim();
        String field = searchFieldField.getText().toLowerCase().trim();

        if (title.isEmpty() && type.isEmpty() && field.isEmpty()) {
            resetPagination();
        } else {
            List<Ressource> searchResult = ressourcesData.stream()
                    .filter(ressource -> (title.isEmpty() || ressource.getTitre_b().toLowerCase().contains(title)) &&
                            (type.isEmpty() || ressource.getType_b().toLowerCase().contains(type)) &&
                            (field.isEmpty() || ressource.getCategorie_resso_b().toLowerCase().contains(field)))
                    .collect(Collectors.toList());
            pag.setPageCount((int) Math.ceil(searchResult.size() / 3.0));
            updatePagination(searchResult);
        }
    }

    private void resetPagination() {
        pag.setPageCount((int) Math.ceil(ressourcesData.size() / 3.0));
        updatePagination(ressourcesData);
    }

    private void updatePagination(List<Ressource> data) {
        pag.setPageFactory(pageIndex -> {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            int itemsPerPage = 3;
            int page = pageIndex * itemsPerPage;
            for (int i = page; i < Math.min(page + itemsPerPage, data.size()); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("itemRessource.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    anchorPane.getStyleClass().add("ct");
                    itemRessourceController itemController = fxmlLoader.getController();
                    itemController.setData(data.get(i));
                    hbox.getChildren().add(anchorPane);
                    HBox.setMargin(anchorPane, new Insets(10));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return hbox;
        });
    }
    private ObservableList<Ressource> ressourcesData = FXCollections.observableArrayList();

    public int getTotalRessources() {
        try {
            RessourceService rs = new RessourceService();
            List<Ressource> ressources = rs.afficher();
            ressourcesData.addAll(ressources);
            return ressources.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
