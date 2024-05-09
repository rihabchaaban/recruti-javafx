package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import services.*;
import models.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import models.User;


public class ModifyUserController implements Initializable{


    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    userService uService = new userService();



    @FXML
    private TextField idCin;

    @FXML
    private TextField idUsername;

    @FXML
    private TextField idCountry;

    @FXML
    private Button idButton;

    @FXML
    private TextField idEmail;

    @FXML
    private ComboBox<String> idRole;

    @FXML
    private TextField idPassword;

    @FXML
    private Button idAfficherAdd;

    @FXML
    private DatePicker idDateBirth;

    String email;

    private final String[] Role = {"Condidate", "Recruter"};
    //private final String[] Sector = {"Manouba", "Ariana", "Marsa", "Megrine", "Zahra", "Charguia"};


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        idRole.setItems(FXCollections.observableArrayList(Role));



    }

    public void populateFields(User user) {
        // Populate form fields with user's information
        idUsername.setText(user.getUsername());
        idEmail.setText(user.getEmail());
        email=user.getEmail();
        idCountry.setText(user.getCountry());
        idPassword.setText(user.getPassword());
        idRole.setValue(user.getRole());
        idCin.setText(String.valueOf(user.getCin()));
        idDateBirth.setValue(user.getDate_birth());




    }

    public void testSaisis(){
        if(this.idUsername.getText().isEmpty() || this.idCountry.getText().isEmpty() || this.idEmail.getText().isEmpty()  || this.idCin.getText().isEmpty() || this.idPassword.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        }
        else if (!this.idEmail.getText().matches(emailRegex)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();

        }else if (uService.checkEmailExists(idEmail.getText())) {

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
        else if (idPassword.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit avoir au moins 8 caractères.");
            alert.showAndWait();
        }   else if (!idPassword.getText().matches(".*[A-Z].*") || !idPassword.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins une lettre majuscule et un chiffre.");
            alert.showAndWait();
        }
        else{
            saveUser();
        }
    }

    @FXML
    void saveUser() {
//
            try {
                uService.modifierByEmail( new User(idUsername.getText(), idEmail.getText(), idCountry.getText(), Integer.parseInt(idCin.getText()), idRole.getValue(), idDateBirth.getValue(), idPassword.getText()),email);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
