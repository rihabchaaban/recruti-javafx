package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import models.Biblio;
import models.Ressource;
import services.BiblioService;
import services.RessourceService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class statistics implements Initializable {

    @FXML
    private Label totalLibrariesLabel;

    @FXML
    private Label totalResourcesLabel;

    @FXML
    private BarChart<String, Number> resourcesPerLibraryChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayTotalLibraries();
        displayTotalResources();
        displayResourcesPerLibrary();
    }

    private void displayTotalLibraries() {
        BiblioService biblioService = new BiblioService();
        int totalLibraries = biblioService.afficher().size();
        totalLibrariesLabel.setText("Total Libraries : " + totalLibraries);
    }

    private void displayTotalResources() {
        RessourceService ressourceService = new RessourceService();
        int totalResources = ressourceService.afficher().size();
        totalResourcesLabel.setText("Total Resources : " + totalResources);
    }

    private void displayResourcesPerLibrary() {
        BiblioService biblioService = new BiblioService();
        List<Biblio> libraries = biblioService.afficher();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Resources per Library");

        for (Biblio library : libraries) {
            RessourceService ressourceService = new RessourceService();
            List<Ressource> resources = ressourceService.getByBiblio(library);
            series.getData().add(new XYChart.Data<>(library.getNom_b(), resources.size()));
        }

        resourcesPerLibraryChart.getData().add(series);

    }
}
