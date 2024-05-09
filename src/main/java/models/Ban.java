package models;

import java.sql.Timestamp;

public class Ban {
    private int id,user_id,nb_post ;
    private Timestamp date_ban;

    public Ban() {
    }

    public Ban(int user_id, int nb_post) {
        this.user_id = user_id;
        this.nb_post = nb_post;
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

    public int getNb_post() {
        return nb_post;
    }

    public void setNb_post(int nb_post) {
        this.nb_post = nb_post;
    }

    public Timestamp getDate_ban() {
        return date_ban;
    }

    public void setDate_ban(Timestamp date_ban) {
        this.date_ban = date_ban;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", nb_post=" + nb_post +
                ", date_ban=" + date_ban +
                '}';
    }
}
