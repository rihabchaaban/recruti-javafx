package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.*;

import models.*;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class ListUserController implements Initializable {

    private  userService ps = new userService();

    @FXML
    private Button idDelete;
    /*
    @FXML
    private TableColumn<User, Integer> idUser;
        */
    @FXML
    private TableColumn<User, String> emailUser;

    @FXML
    private TableColumn<User, Integer> idCin;

    @FXML
    private TextField idSearch;

    @FXML
    private Button addAffich;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> idUsername;


    @FXML
    private TableColumn<User, String> idCountry;


    @FXML
    private TableColumn<User, String> roleUser;

    @FXML
    private TableColumn<User, Date> idDate;

    @FXML
    private ComboBox<String> idSearchWith;

    @FXML
    private ComboBox<String> idSort;






    private final String[] attributsSearch = {"Username", "Cin", "Email_user", "Country", "Role"};
    private final String[] attributsSort = {"Username", "Cin", "Email_user", "Country", "Role"};



    @Override
    public void initialize(URL url, ResourceBundle rb) {



        idSearchWith.setItems(FXCollections.observableArrayList(attributsSearch));
        idSort.setItems(FXCollections.observableArrayList(attributsSort));

        try {
            List<User> users = ps.recuperer();
            ObservableList<User> observableList = FXCollections.observableList(users);

            tableView.setItems(observableList);
            /*idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));*/
            emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
            idCin.setCellValueFactory(new PropertyValueFactory<>("Cin"));
            idCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
            idUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
            roleUser.setCellValueFactory(new PropertyValueFactory<>("Role"));
            idDate.setCellValueFactory(new PropertyValueFactory<>("date_birth"));

            addButtonsToTable();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
   public void initialize() {

    }



    @FXML
    private void export(ActionEvent event) {
        ExcelExporter d = new ExcelExporter();
        d.generateExcel(tableView);
    }

    @FXML
    private void exportToPDF(ActionEvent event) {
        PDFExporter pdfExporter = new PDFExporter();
        pdfExporter.generatePDF(tableView);
    }


    @FXML
    void naviguezVersAjout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterPersonnes.fxml"));
            idSearch.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void showStatistics(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardUser.fxml"));
            Parent root = loader.load();
            DashboardController dashboardController = loader.getController();
          //  dashboardController.loadStatistics();
           // dashboardController.loadStatisticsRating();

            idSearch.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addButtonsToTable() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {

                    private final Button modifierButton = new Button("Modifier");
                    private final Button supprimerButton = new Button("Supprimer");

                    {
                        modifierButton.setOnAction((ActionEvent event) -> {
                            User selectedUser = tableView.getSelectionModel().getSelectedItem();
                            if(selectedUser!=null)
                              naviguezVersModification();
                            else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText("No user Selected");
                                alert.showAndWait();
                            }
                        });

                        supprimerButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());

                            Alert A = new Alert(Alert.AlertType.CONFIRMATION);
                            A.setContentText("Voulez vous supprimer\nL'utilisateur  : " + user.getUsername() +" avec l'email : "+ user.getEmail()+" ?");
                            Optional<ButtonType> result = A.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                            try {
                                ps.supprimer(user.getId());
                                refrechire();
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("user deleted");
                                alert.setHeaderText(null);
                                alert.setContentText("user "+user.getUsername() +" has been deleted successfully");
                                alert.showAndWait();
                            }
                            catch (SQLException e){
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText(e.getMessage());
                                alert.showAndWait();
                            }

                            System.out.println("Deleting user: " + user); }// Replace with actual deletion logic
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(modifierButton, supprimerButton);
                            hbox.setSpacing(10);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        };

        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(cellFactory);

        tableView.getColumns().add(actionColumn);
    }




    @FXML
    private void searchauto() {

        String searchAttribute = idSearchWith.getValue();
        String searchKeyword = idSearch.getText();

        if (searchKeyword.isEmpty()) {
            initialize();

        }

        try {
            List<User> searchResults = ps.searchUsersByEmailStartingWithLetter(searchAttribute,searchKeyword);

            ObservableList<User> observableList = FXCollections.observableList(searchResults);

            tableView.setItems(observableList);
            /*idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));*/
            emailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
            idCin.setCellValueFactory(new PropertyValueFactory<>("Cin"));
            idCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
            idUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
            roleUser.setCellValueFactory(new PropertyValueFactory<>("Role"));
            idDate.setCellValueFactory(new PropertyValueFactory<>("date_birth"));


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void naviguezVersModification() {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyUser.fxml"));
            Parent root = loader.load();

            ModifyUserController modifyUserController = loader.getController();

            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            try {
                modifyUserController.populateFields(selectedUser);
            }
            catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            }

            idSearch.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void refrechire() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            idSearch.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    private void sortData() {
        String  selectedSortOption = idSort.getValue();

        if (selectedSortOption != null) {

            switch (selectedSortOption) {
                case "Username":
                    idUsername.setSortType(TableColumn.SortType.ASCENDING);
                    tableView.getSortOrder().setAll(idUsername);
                    break;
               case "Country":
                    idCountry.setSortType(TableColumn.SortType.ASCENDING);
                    tableView.getSortOrder().setAll(idCountry);
                    break;
               case "Email_user":
                    emailUser.setSortType(TableColumn.SortType.ASCENDING);
                    tableView.getSortOrder().setAll(emailUser);
                    break;
                case "Cin":
                    idCin.setSortType(TableColumn.SortType.ASCENDING);
                    tableView.getSortOrder().setAll(idCin);
                    break;
                case "Role":
                    roleUser.setSortType(TableColumn.SortType.ASCENDING);
                    tableView.getSortOrder().setAll(roleUser);
                    break;
                default:
                    initialize();
                    break;
            }
        } else {

            initialize();
        }
    }






}







