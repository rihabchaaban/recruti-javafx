package entities;

import java.util.Date;

public class Ressource {
    private int id;
    private int biblio_id;
    private String titre_b;
    private String type_b;
    private Date date_publica_b;
    private String categorie_resso_b;
    private String description_b;
    private String image_b_ressource;

    // Constructor

    public Ressource() {
    }

    public Ressource(int id, int biblio_id, String titre_b, String type_b, Date date_publica_b, String categorie_resso_b, String description_b, String image_b_ressource) {
        this.id = id;
        this.biblio_id = biblio_id;
        this.titre_b = titre_b;
        this.type_b = type_b;
        this.date_publica_b = date_publica_b;
        this.categorie_resso_b = categorie_resso_b;
        this.description_b = description_b;
        this.image_b_ressource = image_b_ressource;
    }

    public Ressource(int biblio_id, String titre_b, String type_b, Date date_publica_b, String categorie_resso_b, String description_b, String image_b_ressource) {
        this.biblio_id = biblio_id;
        this.titre_b = titre_b;
        this.type_b = type_b;
        this.date_publica_b = date_publica_b;
        this.categorie_resso_b = categorie_resso_b;
        this.description_b = description_b;
        this.image_b_ressource = image_b_ressource;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBiblio_id() {
        return biblio_id;
    }

    public void setBiblio_id(int biblio_id) {
        this.biblio_id = biblio_id;
    }

    public String getTitre_b() {
        return titre_b;
    }

    public void setTitre_b(String titre_b) {
        this.titre_b = titre_b;
    }

    public String getType_b() {
        return type_b;
    }

    public void setType_b(String type_b) {
        this.type_b = type_b;
    }

    public Date getDate_publica_b() {
        return date_publica_b;
    }

    public void setDate_publica_b(Date date_publica_b) {
        this.date_publica_b = date_publica_b;
    }

    public String getCategorie_resso_b() {
        return categorie_resso_b;
    }

    public void setCategorie_resso_b(String categorie_resso_b) {
        this.categorie_resso_b = categorie_resso_b;
    }

    public String getDescription_b() {
        return description_b;
    }

    public void setDescription_b(String description_b) {
        this.description_b = description_b;
    }

    public String getImage_b_ressource() {
        return image_b_ressource;
    }

    public void setImage_b_ressource(String image_b_ressource) {
        this.image_b_ressource = image_b_ressource;
    }

    // Display

    @Override
    public String toString() {
        return "Ressource{" +
                "id=" + id +
                ", biblio_id=" + biblio_id +
                ", titre_b='" + titre_b + '\'' +
                ", type_b='" + type_b + '\'' +
                ", date_publica_b=" + date_publica_b +
                ", categorie_resso_b='" + categorie_resso_b + '\'' +
                ", description_b='" + description_b + '\'' +
                ", image_b_ressource='" + image_b_ressource + '\'' +
                '}';
    }
}
