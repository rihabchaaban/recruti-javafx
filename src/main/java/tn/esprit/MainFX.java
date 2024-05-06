package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPublications.fxml"));
         Parent root  = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Recruti");
        //  stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
       // stage.initStyle(StageStyle.UNDECORATED);



        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
