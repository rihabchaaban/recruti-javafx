package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import models.User;
import services.userService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private userService userService = new userService();

    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChart2;


    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            pieChart.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStatistics();
        //loadStatisticsRating();
    }

/*
    public void loadStatisticsRating() {
        try {
            List<User> users = userService.recuperer();

            // Initialize counts for different rating ranges
            int excellentCount = 0;
            int goodCount = 0;
            int averageCount = 0;
            int poorCount = 0;

            // Count occurrences of each rating range
            for (User user : users) {
                double rating = user.getRating();
                if (rating >= 4.5) {
                    excellentCount++;
                } else if (rating >= 3.5) {
                    goodCount++;
                } else if (rating >= 2.5) {
                    averageCount++;
                } else {
                    poorCount++;
                }
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Excellent (4.5+)", excellentCount),
                    new PieChart.Data("Good (3.5-4.49)", goodCount),
                    new PieChart.Data("Average (2.5-3.49)", averageCount),
                    new PieChart.Data("Poor (<2.5)", poorCount)
            );

            // Set pie chart data
            pieChart2.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

*/
    public void loadStatistics() {
        try {
            List<User> users = userService.recuperer();

            // Initialize a map to store role counts
            Map<String, Integer> roleCounts = new HashMap<>();

            // Count occurrences of each role
            for (User user : users) {
                String role = user.getRole().toLowerCase();
                roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Add data for each role to the pie chart
            for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Set pie chart data
            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
