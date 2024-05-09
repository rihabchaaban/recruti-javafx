package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Ressource;
import services.RessourceService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class listRessourceController implements Initializable {

    @FXML
    private AnchorPane listRessourcePane;

    @FXML
    private HBox hBox;

    @FXML
    private HBox vbox;

    @FXML
    private Pagination pag;

    @FXML
    private Label totalRessourcesLabel;

    @FXML
    private TextField searchFieldTitle;

    @FXML
    private TextField searchFieldType;

    @FXML
    private TextField searchFieldField;


    private ObservableList<Ressource> ressourcesData = FXCollections.observableArrayList();

    public int getTotalRessources() {
        try {
            RessourceService rs = new RessourceService();
            List<Ressource> ressources = rs.afficher();
            ressourcesData.addAll(ressources);
            return ressources.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int totalRessources = getTotalRessources();
            totalRessourcesLabel.setText("Total Resources : " + totalRessources);
            RessourceService rs = new RessourceService();
            List<Ressource> res = rs.afficher();
            pag.setPageCount((int) Math.ceil(res.size() / 3.0));
            pag.setPageFactory(pageIndex -> {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setAlignment(Pos.CENTER);
                int itemsPerPage = 3;
                int page = pageIndex * itemsPerPage;
                for (int i = page; i < Math.min(page + itemsPerPage, res.size()); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/itemRessource.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        anchorPane.getStyleClass().add("ct");
                        itemRessourceController itemController = fxmlLoader.getController();
                        itemController.setData(res.get(i));
                        hbox.getChildren().add(anchorPane);
                        HBox.setMargin(anchorPane, new Insets(10));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return hbox;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String title = searchFieldTitle.getText().toLowerCase().trim();
        String type = searchFieldType.getText().toLowerCase().trim();
        String field = searchFieldField.getText().toLowerCase().trim();

        if (title.isEmpty() && type.isEmpty() && field.isEmpty()) {
            resetPagination();
        } else {
            List<Ressource> searchResult = ressourcesData.stream()
                    .filter(ressource -> (title.isEmpty() || ressource.getTitre_b().toLowerCase().contains(title)) &&
                            (type.isEmpty() || ressource.getType_b().toLowerCase().contains(type)) &&
                            (field.isEmpty() || ressource.getCategorie_resso_b().toLowerCase().contains(field)))
                    .collect(Collectors.toList());
            pag.setPageCount((int) Math.ceil(searchResult.size() / 3.0));
            updatePagination(searchResult);
        }
    }


    private void resetPagination() {
        pag.setPageCount((int) Math.ceil(ressourcesData.size() / 3.0));
        updatePagination(ressourcesData);
    }

    private void updatePagination(List<Ressource> data) {
        pag.setPageFactory(pageIndex -> {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            int itemsPerPage = 3;
            int page = pageIndex * itemsPerPage;
            for (int i = page; i < Math.min(page + itemsPerPage, data.size()); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/itemRessource.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    anchorPane.getStyleClass().add("ct");
                    itemRessourceController itemController = fxmlLoader.getController();
                    itemController.setData(data.get(i));
                    hbox.getChildren().add(anchorPane);
                    HBox.setMargin(anchorPane, new Insets(10));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return hbox;
        });
    }

    @FXML
    private void sortByTitle() {
        ressourcesData.sort(Comparator.comparing(Ressource::getTitre_b));
        updatePagination(ressourcesData);
    }

    @FXML
    private void sortByDate() {
        ressourcesData.sort(Comparator.comparing(Ressource::getDate_publica_b));
        updatePagination(ressourcesData);
    }

    @FXML
    private void sortByDateDescending() {
        ressourcesData.sort(Comparator.comparing(Ressource::getDate_publica_b).reversed());
        updatePagination(ressourcesData);
    }
    @FXML
    void genererPDF(MouseEvent event) {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(getWindow(event));

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Ajouter le contenu au document
                addContentToPDF(document);

                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour ajouter le contenu au document PDF
    private void addContentToPDF(Document document) throws DocumentException, SQLException {
        RessourceService rs = new RessourceService();
        List<Ressource> ressources = rs.afficher();

        // Créer une police personnalisée pour les titres
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
        BaseColor titleColor = new BaseColor(114, 0, 0); //
        fontTitle.setColor(titleColor);
        // Créer une police personnalisée pour les données
        Font fontData = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);

        // Créer un paragraphe pour le titre
        Paragraph title = new Paragraph("Resources Listing ", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Créer une table pour afficher les données
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell(new PdfPCell(new Paragraph("Title", fontTitle)));
        table.addCell(new PdfPCell(new Paragraph("Type", fontTitle)));
        table.addCell(new PdfPCell(new Paragraph("Field", fontTitle)));
        table.addCell(new PdfPCell(new Paragraph("Publication Date", fontTitle)));

        // Ajouter les données des ressources à la table
        for (Ressource ressource : ressources) {
            table.addCell(new PdfPCell(new Paragraph(ressource.getTitre_b(), fontData)));
            table.addCell(new PdfPCell(new Paragraph(ressource.getType_b(), fontData)));
            table.addCell(new PdfPCell(new Paragraph(ressource.getCategorie_resso_b(), fontData)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(ressource.getDate_publica_b()), fontData)));
        }

        document.add(table);
        System.out.println(" PDF generated Successfully.");

    }

    // Méthode utilitaire pour obtenir la fenêtre parente
    private Window getWindow(MouseEvent event) {
        return ((Node) event.getSource()).getScene().getWindow();
    }

}
