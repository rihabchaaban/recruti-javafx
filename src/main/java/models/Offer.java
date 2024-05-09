package models;

import java.util.Date;

public class Offer {
    private int id;
    private String titre_o;
    private String description_o;
    private String type_o;
    private String localisation_o;;
    private Date date_o;
    private String dure_o;
    private String salaire_o;
    private int user_id;


    public Offer(String titre_o, String description_o, String type_o, String localisation_o, Date date_o, String dure_o, String salaire_o, int user_id) {
        this.titre_o = titre_o;
        this.description_o = description_o;
        this.type_o = type_o;
        this.localisation_o = localisation_o;
        this.date_o = date_o;
        this.dure_o = dure_o;
        this.salaire_o = salaire_o;
        this.user_id = user_id;
    }

    public Offer(String titre_o, String description_o, String type_o, String localisation_o, Date date_o, String dure_o, String salaire_o) {
        this.titre_o = titre_o;
        this.description_o = description_o;
        this.type_o = type_o;
        this.localisation_o = localisation_o;
        this.date_o = date_o;
        this.dure_o = dure_o;
        this.salaire_o = salaire_o;
    }

    public Offer(int id, String titre_o, String description_o, String type_o, String localisation_o, Date date_o, String dure_o, String salaire_o, int user_id) {
        this.id = id;
        this.titre_o = titre_o;
        this.description_o = description_o;
        this.type_o = type_o;
        this.localisation_o = localisation_o;
        this.date_o = date_o;
        this.dure_o = dure_o;
        this.salaire_o = salaire_o;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre_o() {
        return titre_o;
    }

    public void setTitre_o(String titre_o) {
        this.titre_o = titre_o;
    }

    public String getDescription_o() {
        return description_o;
    }

    public void setDescription_o(String description_o) {
        this.description_o = description_o;
    }

    public String getType_o() {
        return type_o;
    }

    public void setType_o(String type_o) {
        this.type_o = type_o;
    }

    public String getLocalisation_o() {
        return localisation_o;
    }

    public void setLocalisation_o(String localisation_o) {
        this.localisation_o = localisation_o;
    }

    public Date getDate_o() {
        return date_o;
    }

    public void setDate_o(Date date_o) {
        this.date_o = date_o;
    }

    public String getDure_o() {
        return dure_o;
    }

    public void setDure_o(String dure_o) {
        this.dure_o = dure_o;
    }

    public String getSalaire_o() {
        return salaire_o;
    }

    public void setSalaire_o(String salaire_o) {
        this.salaire_o = salaire_o;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
