package models;

import java.util.Date;

public class Biblio {
    private int id;
    private String nom_b;
    private String domaine_b;
    private Date date_creation_b;
    private String image_b;

    //Constructor

    public Biblio() {
    }

    public Biblio(int id, String nom_b, String domaine_b, Date date_creation_b, String image_b) {
        this.id = id;
        this.nom_b = nom_b;
        this.domaine_b = domaine_b;
        this.date_creation_b = date_creation_b;
        this.image_b = image_b;
    }

    public Biblio(String nom_b, String domaine_b, Date date_creation_b, String image_b) {
        this.nom_b = nom_b;
        this.domaine_b = domaine_b;
        this.date_creation_b = date_creation_b;
        this.image_b = image_b;
    }
    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_b() {
        return nom_b;
    }

    public void setNom_b(String nom_b) {
        this.nom_b = nom_b;
    }

    public String getDomaine_b() {
        return domaine_b;
    }

    public void setDomaine_b(String domaine_b) {
        this.domaine_b = domaine_b;
    }

    public Date getDate_creation_b() {
        return date_creation_b;
    }

    public void setDate_creation_b(Date date_creation_b) {
        this.date_creation_b = date_creation_b;
    }

    public String getImage_b() {
        return image_b;
    }

    public void setImage_b(String image_b) {
        this.image_b = image_b;
    }

    //Display

    @Override
    public String toString() {
        return nom_b;
    }

}
