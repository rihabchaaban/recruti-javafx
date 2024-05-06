package gui;
import entities.Ressource;

import entities.Biblio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.BiblioService;
import services.RessourceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

}
