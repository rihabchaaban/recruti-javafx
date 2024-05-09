package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.SMSSender;
import models.User;
import services.userService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    userService uService = new userService();


    @FXML
    private TextField idUsername;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private Button idButtonLogIn;

    @FXML
    private Button idGoogleLogIn;

    @FXML
    private TextField SignUpPassword;

    @FXML
    private TextField idCountry;

    @FXML
    private TextField idCin;

    @FXML
    private ComboBox<String> idRole;

    @FXML
    private DatePicker idDateBirth;
    private final String[] Role = {"Condidate", "Recruter"};


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        idRole.setItems(FXCollections.observableArrayList(Role));



    }




    public void test(){
        if(this.idUsername.getText().isEmpty() || this.idCountry.getText().isEmpty() || this.SignUpEmail.getText().isEmpty()  || this.idCin.getText().isEmpty() || this.SignUpPassword.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        }
        else if (!this.SignUpEmail.getText().matches(emailRegex)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();

        }else if (uService.checkEmailExists(SignUpEmail.getText())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email existe déjà");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email différent !");
            alert.showAndWait();
        }else if(!idCin.getText().matches(".*\\d.*")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format du numero invalide");
            alert.setHeaderText(null);
            alert.setContentText("Format de numero invalide !");
            alert.showAndWait();

        }
        else if (SignUpPassword.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit avoir au moins 8 caractères.");
            alert.showAndWait();
        }   else if (!SignUpPassword.getText().matches(".*[A-Z].*") || !SignUpPassword.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins une lettre majuscule et un chiffre.");
            alert.showAndWait();
        }
        else{
            ajouterUSer();
        }
    }



    @FXML
    void naviguezVersAffichageSignIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void ajouterUSer() {

//        SMSSender smsSender=new SMSSender();
//        smsSender.send_SMS(idUsername.getText());

        try {
            uService.ajouter(new User(idUsername.getText(), SignUpEmail.getText(), idCountry.getText(), Integer.parseInt(idCin.getText()), idRole.getValue(), idDateBirth.getValue(), SignUpPassword.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }




}
