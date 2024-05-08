module com.example.gestionoffre {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires twilio;
    requires itextpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens gui to javafx.fxml;
    exports gui;
}