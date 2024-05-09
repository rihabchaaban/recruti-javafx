package controllers;

import models.RendezVous;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public static void generatePDF(List<RendezVous> rendezVousData) throws IOException {
        // Vérifier si la liste n'est pas vide
        if (!rendezVousData.isEmpty()) {
            try (PDDocument document = new PDDocument()) {
                // Récupérer le dernier rendez-vous ajouté
                RendezVous lastRendezVous = rendezVousData.get(rendezVousData.size() - 1);

                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

                // Définir les marges
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20;
                float cellMargin = 5;

                // Titre
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yStart);
                contentStream.showText(" Appointment");
                contentStream.endText();
                yStart -= 30;

                // Tableau de données
                drawTable(contentStream, margin, yStart, tableWidth, rowHeight, cellMargin, lastRendezVous);

                contentStream.close();

                // Chemin absolu vers le fichier de sortie PDF
                File file = new File("C:/Users/Rihab/Documents/RendezVous.pdf");

                document.save(file);
                document.close();

                // Log to indicate that the document is generated successfully
                System.out.println("PDF generated successfully.");
            } catch (IOException e) {
                // Catch exceptions and print the error message
                e.printStackTrace();
                System.err.println("An error occurred while generating PDF: " + e.getMessage());
            }
        } else {
            System.out.println("List of RendezVous is empty. No PDF generated.");
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float xStart, float yStart, float tableWidth, float rowHeight, float cellMargin, RendezVous rendezVous) throws IOException {
        float y = yStart;

        // En-tête
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        drawCell(contentStream, xStart, y, tableWidth / 2, rowHeight, "Attribute", true);
        drawCell(contentStream, xStart + tableWidth / 2, y, tableWidth / 2, rowHeight, "Value", true);
        y -= rowHeight;

        // Contenu
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        drawCell(contentStream, xStart, y, tableWidth / 2, rowHeight, "Date", false);
        drawCell(contentStream, xStart + tableWidth / 2, y, tableWidth / 2, rowHeight, rendezVous.getDate_rendez().toString(), false);
        y -= rowHeight;

        drawCell(contentStream, xStart, y, tableWidth / 2, rowHeight, "Heure", false);
        drawCell(contentStream, xStart + tableWidth / 2, y, tableWidth / 2, rowHeight, rendezVous.getHeure_rendez(), false);
        y -= rowHeight;

        drawCell(contentStream, xStart, y, tableWidth / 2, rowHeight, "Email Candidat", false);
        drawCell(contentStream, xStart + tableWidth / 2, y, tableWidth / 2, rowHeight, rendezVous.getEmail_condi(), false);
        y -= rowHeight;

        drawCell(contentStream, xStart, y, tableWidth / 2, rowHeight, "Email Representant", false);
        drawCell(contentStream, xStart + tableWidth / 2, y, tableWidth / 2, rowHeight, rendezVous.getEmail_represen(), false);
    }

    private static void drawCell(PDPageContentStream contentStream, float x, float y, float width, float height, String text, boolean isHeader) throws IOException {
        contentStream.setLineWidth(0.5f);
        contentStream.setNonStrokingColor(0f); // Noir par défaut

        if (isHeader) {
            contentStream.fillRect(x, y, width, height);
            contentStream.setNonStrokingColor(1f); // Blanc pour le texte
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(x + 5, y + height / 2);
            contentStream.showText(text);
            contentStream.endText();
        } else {
            contentStream.addRect(x, y, width, height);
            contentStream.stroke();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(x + 5, y + height / 2);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

}
