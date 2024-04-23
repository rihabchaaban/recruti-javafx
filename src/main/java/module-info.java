module fxcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    requires javafx.web;
    requires java.prefs;

    requires com.google.zxing;
    requires org.apache.pdfbox;

    opens  view to javafx.fxml;
    exports app;
    exports entity;
    exports view;
}