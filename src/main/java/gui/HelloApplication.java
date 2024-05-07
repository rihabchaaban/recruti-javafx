package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml")); //Admin
        //Parent root = FXMLLoader.load(getClass().getResource("listBiblioFront.fxml")); //Candidat
        //Parent root = FXMLLoader.load(getClass().getResource("listBiblioFrontRecruteur.fxml")); //Recruteur
        stage.setTitle("Recruti | 2024");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}