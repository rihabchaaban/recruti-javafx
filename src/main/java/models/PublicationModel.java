package models;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PublicationModel {
    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final StringProperty contenu;
    private final ObjectProperty<Timestamp> dateCreationpub;

    public PublicationModel(int id, int userId, String contenu, Timestamp dateCreationpub) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.contenu = new SimpleStringProperty(contenu);
        this.dateCreationpub = new SimpleObjectProperty<>(dateCreationpub);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getContenu() {
        return contenu.get();
    }

    public StringProperty contenuProperty() {
        return contenu;
    }

    public Timestamp getDateCreationpub() {
        return dateCreationpub.get();
    }

    public ObjectProperty<Timestamp> dateCreationpubProperty() {
        return dateCreationpub;
    }
}

