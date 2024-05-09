package models;

import java.sql.Timestamp;

public class Like {
    private int id,user_id,publication_id;
    private Timestamp date_creation_like;

    public Like() {
    }

    public Like(int id, int user_id, int publication_id, Timestamp date_creation_like) {
        this.id = id;
        this.user_id = user_id;
        this.publication_id = publication_id;
        this.date_creation_like = date_creation_like;
    }

    public Like(int user_id, int publication_id) {
        this.user_id = user_id;
        this.publication_id = publication_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id = publication_id;
    }

    public Timestamp getDate_creation_like() {
        return date_creation_like;
    }

    public void setDate_creation_like(Timestamp date_creation_like) {
        this.date_creation_like = date_creation_like;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", publication_id=" + publication_id +
                ", date_creation_like=" + date_creation_like +
                '}';
    }

}

