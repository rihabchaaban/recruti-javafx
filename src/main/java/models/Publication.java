package models;

import java.sql.Timestamp;


public class Publication {
    //Att
    private String contenu;
    private int id,user_id ;
    private Timestamp date_creationpub;



//constructor
    public Publication() {
    }

    public Publication(String contenu, int id, int user_id,Timestamp date_creationpub) {
        this.contenu = contenu;
        this.id = id;
        this.user_id = user_id;
        this.date_creationpub = date_creationpub;
    }

    public Publication(String contenu, int user_id) {
        this.contenu = contenu;
        this.user_id = user_id;

    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public Timestamp getDate_creationpub() {
        return date_creationpub;
    }

    public void setDate_creationpub(Timestamp date_creationpub) {
        this.date_creationpub = date_creationpub;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "contenu='" + contenu + '\'' +
                ", id=" + id +
                ", user_id=" + user_id +
                ", date_creationpub=" + date_creationpub +
                '}';
    }
}
