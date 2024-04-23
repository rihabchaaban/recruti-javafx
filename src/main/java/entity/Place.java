package entity;

public class Place {

    int id ;
    String gouvernement, ville;
    float altitude , longitude;

    public Place() {
    }

    public Place(int id, String gouvernement, String ville, float altitude, float longitude) {
        this.id = id;
        this.gouvernement = gouvernement;
        this.ville = ville;
        this.altitude = altitude;
        this.longitude = longitude;
    }

    public Place(String gouvernement, String ville, float altitude, float longitude) {
        this.gouvernement = gouvernement;
        this.ville = ville;
        this.altitude = altitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGouvernement() {
        return gouvernement;
    }

    public void setGouvernement(String gouvernement) {
        this.gouvernement = gouvernement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
