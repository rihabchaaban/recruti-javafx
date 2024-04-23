package app;

import java.io.IOException;
import java.sql.SQLException;


import entity.RendezVous;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pidevclient.services.IService;
import pidevclient.services.RendezVousService;


public class App extends Application  {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<RendezVous> personData = FXCollections.observableArrayList();

	public App() throws SQLException {

	}

	public ObservableList<RendezVous> getPersonData() {
		return personData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("A Showcase Application");

		initializeLayout();
		showOverview();
	}

	public void initializeLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();


			rootLayout.setPrefWidth(100);
			rootLayout.setPrefHeight(100);


			Scene scene = new Scene(rootLayout);


			primaryStage.setScene(scene);


			primaryStage.setTitle("Plain Window");


			primaryStage.setResizable(true);


			AnchorPane personOverview = FXMLLoader.load(App.class.getResource("/displayRendezVous.fxml"));
			rootLayout.setCenter(personOverview);


			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void showOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/displayRendezVous.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);

			IService<RendezVous> display = new RendezVousService();
			for (RendezVous item : display.getAll()) {
				personData.add(item);
			}




		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}





	public static void main(String[] args) {
		launch(args);
	}
}
