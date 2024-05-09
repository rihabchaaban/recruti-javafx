package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import services.*;

public class ResetPasswordController {
    @FXML
    private Button idBackCode;

    @FXML
    private PasswordField idConfirmNewPassword;

    @FXML
    private Button IdContinueCode;

    @FXML
    private PasswordField idNewPassword;

    @FXML
    private void btnResetAction(ActionEvent event) {
        Alert A = new Alert(Alert.AlertType.INFORMATION);
        if (!idNewPassword.getText().equals("") && idNewPassword.getText().equals(idConfirmNewPassword.getText())) {
            userService su = new userService();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgotPassword.fxml"));
                Parent root = loader.load();

            ForgotPasswordController forgotPasswordController = loader.getController();
            su.ResetPassword(forgotPasswordController.EmailReset, idNewPassword.getText());
            A.setContentText("Mot de passe modifié avec succes ! ");
            A.show();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            try {

                Parent page1 = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));

                Scene scene = new Scene(page1);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(scene);

                stage.show();

            } catch (IOException ex) {

                System.out.println(ex.getMessage());

            }
        } else {
            A.setContentText("veuillez saisir un mot de passe conforme !");
            A.show();
        }

    }

}
