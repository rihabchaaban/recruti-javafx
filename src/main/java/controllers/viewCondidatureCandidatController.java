package controllers;

import models.Condidature;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CondidatureService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class viewCondidatureCandidatController implements Initializable {
    @FXML
    private AnchorPane viewCondidatureCandiatPane;

    @FXML
    private HBox vBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CondidatureService cs = new CondidatureService();
            List<Condidature> conds = cs.afficher();

            for(int i=0;i<conds.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemCondidature.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    VBox vBox1 = (VBox) anchorPane.getChildren().get(0);
                    itemCondidatureController itemController = fxmlLoader.getController();
                    itemController.setData(conds.get(i));
                    vBox.getChildren().add(vBox1);
                } catch (IOException ex) {
                    Logger.getLogger(itemCondidatureController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
