package tn.esprit.models;

import java.sql.Timestamp;

public class Commentaire {
    private String contenu_com;
    private int id,user_id,publication_id;
    private Timestamp date_creation_com;

    public Commentaire() {
    }

    public Commentaire(String contenu_com, int user_id, int publication_id) {
        this.contenu_com = contenu_com;
        this.user_id = user_id;
        this.publication_id = publication_id;

    }

    public Commentaire(String contenu_com, int id, int user_id, int publication_id, Timestamp date_creation_com) {
        this.contenu_com = contenu_com;
        this.id = id;
        this.user_id = user_id;
        this.publication_id = publication_id;
        this.date_creation_com = date_creation_com;
    }

    public String getContenu_com() {
        return contenu_com;
    }

    public void setContenu_com(String contenu_com) {
        this.contenu_com = contenu_com;
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

    public Timestamp getDate_creation_com() {
        return date_creation_com;
    }

    public void setDate_creation_com(Timestamp date_creation_com) {
        this.date_creation_com = date_creation_com;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "contenu_com='" + contenu_com + '\'' +
                ", id=" + id +
                ", user_id=" + user_id +
                ", publication_id=" + publication_id +
                ", date_creation_com=" + date_creation_com +
                '}';
    }
}
