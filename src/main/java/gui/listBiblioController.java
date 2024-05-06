package gui;
import entities.Biblio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
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
public class listBiblioController implements Initializable{
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BiblioService os = new BiblioService();
            List<Biblio> biblios = os.afficher();
            pag.setPageCount((int) Math.ceil(biblios.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, biblios.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("itemBiblio.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        itemBiblioController itemController = fxmlLoader.getController();
                        itemController.setData(biblios.get(i));
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
}
