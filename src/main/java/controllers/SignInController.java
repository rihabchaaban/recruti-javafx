package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.User;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class SignInController {

    public static int id_modif;

    userService uService = new userService();
    @FXML
    private Button idAppleLogIn;

    @FXML
    private Button idButtonLogIn;

    @FXML
    private TextField LogInPassword;

    @FXML
    private CheckBox idRemeberMeLogIn;

    @FXML
    private Button idFbLogIn;

    @FXML
    private TextField LogInEmail;

    @FXML
    private Button idGoogleLogIn;
    public User loggedInUser;

    // Méthode pour récupérer l'utilisateur connecté
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Méthode pour définir l'utilisateur connecté
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @FXML
    private void oublier(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));
            LogInEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
//    @FXML
//    private void inscri(ActionEvent event) {
//
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("inscriptionUser.fxml"));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();} catch (IOException ex) {
//            Logger.getLogger(InscriptionUserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @FXML
    void naviguezVersSignUp(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
            LogInEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    private void cnx(ActionEvent event) throws SQLException {
        //String page = "";
        String email = LogInEmail.getText();
        String password = LogInPassword.getText();
        String Role = null;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email.isEmpty() || password.isEmpty()) {
            // Afficher un message d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        } else if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();
        } else {
            Role = uService.authentification(email, password);
            if (Role == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Infos incorrectes");
                alert.setHeaderText(null);
                alert.setContentText("Email ou Mot de passe incorrecte !");
                alert.showAndWait();
            } else if (Role != null) {
                naviguezVersAffichageSignIn();  //add if else selon le role de l'utilisateur
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Infos incorrectes");
                alert.setHeaderText(null);
                alert.setContentText("vous n'étes pas un admin !");
                alert.showAndWait();
            }
            if (Role != null) {
                uService.setUser(uService.getUserFromDatabase(email, password));
                System.out.println(userService.getUser().toString());
                setLoggedInUser(userService.getUser());
                System.out.println(loggedInUser);
            }


        }


    }
    // naviguezVersAffichageSignIn();


    @FXML
    void naviguezVersAffichageSignIn() {
        String email = LogInEmail.getText();
        String password = LogInPassword.getText();
        String Role = null;
        Role = uService.authentification(email, password);
        try {
            if(Role.equals("Admin")) {
            Parent root = FXMLLoader.load(getClass().getResource("/hello-view.fxml"));
            LogInEmail.getScene().setRoot(root);}
            else { Parent root = FXMLLoader.load(getClass().getResource("/HelloFrontR.fxml"));
                LogInEmail.getScene().setRoot(root);}
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

