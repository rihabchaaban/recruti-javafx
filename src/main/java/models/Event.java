package models;

public class Event {
    private int id;
    private String nom_e;
    private String heure_e;
    private String lieu_e;
    private String description;
    private String image_e;
    private String theme_e;
    private String contact_e;
    private String date_e;


    public Event() {
    }

    public Event(int id, String nom_e, String heure_e, String lieu_e, String description, String image_e, String theme_e, String contact_e, String date_e) {
        this.id = id;
        this.nom_e = nom_e;
        this.heure_e = heure_e;
        this.lieu_e = lieu_e;
        this.description = description;
        this.image_e = image_e;
        this.theme_e = theme_e;
        this.contact_e = contact_e;
        this.date_e = date_e;
    }

    public Event(String nom_e, String heure_e, String lieu_e, String description, String image_e, String theme_e, String contact_e, String date_e) {
        this.nom_e = nom_e;
        this.heure_e = heure_e;
        this.lieu_e = lieu_e;
        this.description = description;
        this.image_e = image_e;
        this.theme_e = theme_e;
        this.contact_e = contact_e;
        this.date_e = date_e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_e() {
        return nom_e;
    }

    public void setNom_e(String nom_e) {
        this.nom_e = nom_e;
    }

    public String getHeure_e() {
        return heure_e;
    }

    public void setHeure_e(String heure_e) {
        this.heure_e = heure_e;
    }

    public String getLieu_e() {
        return lieu_e;
    }

    public void setLieu_e(String lieu_e) {
        this.lieu_e = lieu_e;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_e() {
        return image_e;
    }

    public void setImage_e(String image_e) {
        this.image_e = image_e;
    }

    public String getTheme_e() {
        return theme_e;
    }

    public void setTheme_e(String theme_e) {
        this.theme_e = theme_e;
    }

    public String getContact_e() {
        return contact_e;
    }

    public void setContact_e(String contact_e) {
        this.contact_e = contact_e;
    }

    public String getDate_e() {
        return date_e;
    }

    public void setDate_e(String date_e) {
        this.date_e = date_e;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom_e='" + nom_e + '\'' +
                ", heure_e='" + heure_e + '\'' +
                ", lieu_e='" + lieu_e + '\'' +
                ", description='" + description + '\'' +
                ", image_e='" + image_e + '\'' +
                ", theme_e='" + theme_e + '\'' +
                ", contact_e='" + contact_e + '\'' +
                ", date_e=" + date_e +
                '}';
    }
}

