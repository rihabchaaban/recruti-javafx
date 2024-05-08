module com.example.gestionoffre {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires twilio;


    opens gui to javafx.fxml;
    exports gui;
}