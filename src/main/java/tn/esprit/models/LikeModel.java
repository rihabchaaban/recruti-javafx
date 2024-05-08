package tn.esprit.models;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class LikeModel {
    private final IntegerProperty id;
    private final IntegerProperty user_Id;
    private final IntegerProperty publication_id;
    private final ObjectProperty<Timestamp> date_creation_like;

    public LikeModel(int id, int userId, int publicationId , Timestamp dateCreationLike) {
        this.id = new SimpleIntegerProperty(id);
        this.user_Id = new SimpleIntegerProperty(userId);
        this.publication_id = new SimpleIntegerProperty(publicationId);
        this.date_creation_like = new SimpleObjectProperty<>(dateCreationLike);
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

    public int getUser_Id() {
        return user_Id.get();
    }

    public IntegerProperty user_IdProperty() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id.set(user_Id);
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

    public Timestamp getDate_creation_like() {
        return date_creation_like.get();
    }

    public ObjectProperty<Timestamp> date_creation_likeProperty() {
        return date_creation_like;
    }

    public void setDate_creation_like(Timestamp date_creation_like) {
        this.date_creation_like.set(date_creation_like);
    }
}
