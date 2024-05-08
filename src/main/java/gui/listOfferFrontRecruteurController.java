package gui;

import entities.Offer;
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
import services.OfferService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listOfferFrontRecruteurController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refrech();
    }

    @FXML
    void open_addOffer(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("addOfferFront.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Offer");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
        refrech();
    }

    public void refrech(){
        try {
            OfferService os = new OfferService();
            List<Offer> offers = os.afficher();
            pag.setPageCount((int) Math.ceil(offers.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, offers.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("listOfferFrontCardRecruteur.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listOfferFrontCardRecruteurController itemController = fxmlLoader.getController();
                        itemController.setData(offers.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listOfferFrontCardRecruteurController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
