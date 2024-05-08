module esprit.gestionlibraries {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;

    opens gui to javafx.fxml;
    exports gui;
}