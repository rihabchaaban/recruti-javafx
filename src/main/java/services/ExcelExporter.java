package services;


import models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import java.io.File;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;


    public class ExcelExporter {

            public void generateExcel(TableView<User> tableView) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Excel");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
                );
                File file = fileChooser.showSaveDialog(null);

                if (file != null) {
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("Sheet1");

                    // Create header row
                    // Row headerRow = sheet.createRow(0);
                    //String[] headers = {
                    //  "Username", "Email", "Password", "Role",
                    // "Image", "Age", "Sexe"
                    //};
                    // En-têtes des colonnes
                    Row headerRow = sheet.createRow(0);
                    headerRow.createCell(0).setCellValue("Username");
                    headerRow.createCell(1).setCellValue("Email");
                    headerRow.createCell(2).setCellValue("Country");
                    headerRow.createCell(3).setCellValue("Cin");
                    headerRow.createCell(4).setCellValue("Role");

                    // Ajoutez les en-têtes des autres colonnes ici

                    for (int i = 0; i < tableView.getItems().size(); i++) {
                        Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                        User user = tableView.getItems().get(i);

                        dataRow.createCell(0).setCellValue(user.getUsername());
                        dataRow.createCell(1).setCellValue(user.getEmail());
                        dataRow.createCell(2).setCellValue( user.getCountry());
                        dataRow.createCell(3).setCellValue( user.getCin());
                        dataRow.createCell(4).setCellValue(user.getRole());


                    }

                    try (FileOutputStream fileOut = new FileOutputStream(file)) {
                        workbook.write(fileOut);
                        workbook.close();
                        System.out.println("Excel generated successfully.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


