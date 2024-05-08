package gui;

import entities.Offer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import services.OfferService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class statistiquesController implements Initializable {


    @FXML
    private LineChart<String, Integer> lineChartOffer;

    @FXML
    private AnchorPane statPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            statistique();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void statistique() throws SQLException {
        OfferService rs = new OfferService();

        List<Offer> recs = rs.afficher();

        // Créer les axes pour le graphique
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Type Offer");
        yAxis.setLabel("Salaire Offer");

        // Créer la série de données à afficher
        XYChart.Series series = new XYChart.Series();
        series.setName("Statistiques des offres selon leurs Salaires");
        for (Offer rec : recs) {
            series.getData().add(new XYChart.Data<>(rec.getType_o(), Integer.parseInt(rec.getSalaire_o())));
        }

        // Créer le graphique et ajouter la série de données
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des Offres");
        lineChart.getData().add(series);

        // Afficher le graphique dans votre scène
        lineChartOffer.setCreateSymbols(false);
        lineChartOffer.getData().add(series);
    }
}
