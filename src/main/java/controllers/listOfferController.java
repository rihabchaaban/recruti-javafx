package controllers;

import models.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.OfferService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class listOfferController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private HBox hbox;

    @FXML
    private AnchorPane listOfferPane;

    @FXML
    private Pagination pag;

    @FXML
    private HBox vbox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            OfferService os = new OfferService();
            List<Offer> offers = os.afficher();
            pag.setPageCount((int) Math.ceil(offers.size() / 3.0)); // Nombre total de pages nécessaire pour afficher toutes les cartes
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3; // Nombre des sujets à afficher par page
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, offers.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/itemOffer.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        itemOfferController itemController = fxmlLoader.getController();
                        itemController.setData(offers.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10)); // Marges entre les cartes
                    } catch (IOException ex) {
                        Logger.getLogger(itemOfferController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openStat(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/Statistiques.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Statistiques");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }


    String excelFileName = "C:\\Users\\guemr\\Desktop\\gestionOffre\\src\\main\\resources\\gui\\Excel\\offersExcel.xlsx";
    @FXML
    public void exportExcel(ActionEvent event) throws IOException {
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Offers");

        // Define custom styles for the header and data cells
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setWrapText(true);

        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Titre");
        headerRow.createCell(1).setCellValue("Type");
        headerRow.createCell(2).setCellValue("Localisation");
        headerRow.createCell(3).setCellValue("Date");
        headerRow.createCell(4).setCellValue("Duree");
        headerRow.createCell(5).setCellValue("Salaire");



        // Apply the custom header style to the header row cells
        for (Cell headerCell : headerRow) {
            headerCell.setCellStyle(headerStyle);
        }

        // Create data rows
        int rowNum = 1;
        int total = 0;
        OfferService as = new OfferService();
        List<Offer> acts = as.afficher();
        for (Offer act : acts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(act.getTitre_o());
            row.createCell(1).setCellValue(act.getType_o());
            row.createCell(2).setCellValue(act.getLocalisation_o());
            row.createCell(3).setCellValue(act.getDate_o());
            row.createCell(4).setCellValue(act.getDure_o());
            row.createCell(5).setCellValue(act.getSalaire_o());

            total = total + Integer.parseInt(act.getSalaire_o());
            // Apply the custom data style to the data row cells
            for (Cell dataCell : row) {
                dataCell.setCellStyle(dataStyle);
            }
        }

        float moy = (float)total/rowNum;
        Row rowMoy = sheet.createRow(8);
        rowMoy.createCell(2).setCellValue("Moyenne des Salaires : " + moy);

        // Resize columns to fit their content
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Get the current date and time
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formattedDate = dateFormat.format(currentDate);

        // Add the formatted date to the file name
        int extensionIndex = excelFileName.lastIndexOf('.');
        String newFileName = excelFileName.substring(0, extensionIndex) + "_" + formattedDate + ".xlsx";

        // Write the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream(newFileName)) {
            workbook.write(outputStream);
            System.out.println("Export successful!");
        } catch (IOException e) {
            System.out.println("Export Failed.");
        }
    }
}
