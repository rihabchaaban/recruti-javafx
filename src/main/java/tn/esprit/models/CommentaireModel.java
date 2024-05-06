package tn.esprit.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

import javafx.beans.property.*;

public class CommentaireModel {

        private final StringProperty contenu_com;
        private final IntegerProperty id;
        private final IntegerProperty user_id;
        private final IntegerProperty publication_id;
        private final ObjectProperty<Timestamp> date_creation_com;


    public CommentaireModel(StringProperty contenuCom, IntegerProperty id, IntegerProperty userId, IntegerProperty publicationId, ObjectProperty<Timestamp> dateCreationCom) {
        contenu_com = contenuCom;
        this.id = id;
        user_id = userId;
        publication_id = publicationId;
        date_creation_com = dateCreationCom;
    }
    public CommentaireModel(int id, int userId,int pubId, String contenu, Timestamp dateCreationpub) {
        this.id = new SimpleIntegerProperty(id);
        this.user_id = new SimpleIntegerProperty(userId);
        this.publication_id = new SimpleIntegerProperty(pubId);
        this.contenu_com = new SimpleStringProperty(contenu);
        this.date_creation_com = new SimpleObjectProperty<>(dateCreationpub);
    }

    public String getContenu_com() {
        return contenu_com.get();
    }

    public StringProperty contenu_comProperty() {
        return contenu_com;
    }

    public void setContenu_com(String contenu_com) {
        this.contenu_com.set(contenu_com);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getUser_id() {
        return user_id.get();
    }

    public IntegerProperty user_idProperty() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id.set(user_id);
    }

    public int getPublication_id() {
        return publication_id.get();
    }

    public IntegerProperty publication_idProperty() {
        return publication_id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id.set(publication_id);
    }

    public Timestamp getDate_creation_com() {
        return date_creation_com.get();
    }

    public ObjectProperty<Timestamp> date_creation_comProperty() {
        return date_creation_com;
    }

    public void setDate_creation_com(Timestamp date_creation_com) {
        this.date_creation_com.set(date_creation_com);
    }
}
