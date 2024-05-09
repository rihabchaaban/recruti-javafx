package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Biblio;
import services.BiblioService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listBiblioFrontController implements Initializable {

    @FXML
    private ImageView background;

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listRessourceFront;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refrech();
    }

    @FXML
    private AnchorPane listBiblioFront;
    @FXML
    void open_viewRessource(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/listRessourceFront.fxml"));
        Parent fxml = fxmlLoader.load();
        Scene scene = new Scene(fxml);
        Scene currentScene = listBiblioFront.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.close();
        Stage stage = new Stage();
        stage.setTitle("Show Resources");
        stage.setScene(scene);
        stage.showAndWait();
        refrech();

    }
    public void refrech(){
        try {
            BiblioService bs = new BiblioService();
            List<Biblio> biblios = bs.afficher();
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
                        fxmlLoader.setLocation(getClass().getResource("/listBiblioFrontCard.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        listBiblioFrontCardController itemController = fxmlLoader.getController();
                        itemController.setData(biblios.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(listBiblioFrontCardController.class.getName()).log(Level.SEVERE, null, ex);
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
