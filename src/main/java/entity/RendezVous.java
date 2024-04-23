package entity;

import java.sql.Date;

public class RendezVous {
    int id ;
    Date date_rendez ;
    String heure_rendez,email_condi , email_represen ;
    int id_place;

    public RendezVous() {
    }

    public RendezVous(int id, Date date_rendez, String heure_rendez, String email_condi, String email_represen, int id_place) {
        this.id = id;
        this.date_rendez = date_rendez;
        this.heure_rendez = heure_rendez;
        this.email_condi = email_condi;
        this.email_represen = email_represen;
        this.id_place = id_place;
    }

    public RendezVous(Date date_rendez, String heure_rendez, String email_condi, String email_represen, int id_place) {
        this.date_rendez = date_rendez;
        this.heure_rendez = heure_rendez;
        this.email_condi = email_condi;
        this.email_represen = email_represen;
        this.id_place = id_place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_rendez() {
        return date_rendez;
    }

    public void setDate_rendez(Date date_rendez) {
        this.date_rendez = date_rendez;
    }

    public String getHeure_rendez() {
        return heure_rendez;
    }

    public void setHeure_rendez(String heure_rendez) {
        this.heure_rendez = heure_rendez;
    }


    public String getEmail_condi() {
        return email_condi;
    }

    public void setEmail_condi(String email_condi) {
        this.email_condi = email_condi;
    }

    public String getEmail_represen() {
        return email_represen;
    }

    public void setEmail_represen(String email_represen) {
        this.email_represen = email_represen;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }
}
