package entities;

public class Condidature {
    private int id;
    private int offer_id;
    private String nom_c;
    private String email_c;
    private String cv_c;
    private String lettre_mo;
    private int user_id;

    public Condidature(int id, int offer_id, String nom_c, String email_c, String cv_c, String lettre_mo, int user_id) {
        this.id = id;
        this.offer_id = offer_id;
        this.nom_c = nom_c;
        this.email_c = email_c;
        this.cv_c = cv_c;
        this.lettre_mo = lettre_mo;
        this.user_id = user_id;
    }

    public Condidature(int offer_id, String nom_c, String email_c, String cv_c, String lettre_mo, int user_id) {
        this.offer_id = offer_id;
        this.nom_c = nom_c;
        this.email_c = email_c;
        this.cv_c = cv_c;
        this.lettre_mo = lettre_mo;
        this.user_id = user_id;
    }

    public Condidature(int offer_id, String nom_c, String email_c, String cv_c, String lettre_mo) {
        this.offer_id = offer_id;
        this.nom_c = nom_c;
        this.email_c = email_c;
        this.cv_c = cv_c;
        this.lettre_mo = lettre_mo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public String getNom_c() {
        return nom_c;
    }

    public void setNom_c(String nom_c) {
        this.nom_c = nom_c;
    }

    public String getEmail_c() {
        return email_c;
    }

    public void setEmail_c(String email_c) {
        this.email_c = email_c;
    }

    public String getCv_c() {
        return cv_c;
    }

    public void setCv_c(String cv_c) {
        this.cv_c = cv_c;
    }

    public String getLettre_mo() {
        return lettre_mo;
    }

    public void setLettre_mo(String lettre_mo) {
        this.lettre_mo = lettre_mo;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
