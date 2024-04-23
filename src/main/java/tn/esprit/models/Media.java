package tn.esprit.models;

import java.sql.Timestamp;
import java.util.Arrays;

public class Media {
    private String chemin,type;
    private int id,publication_id ;
    private byte[] data;

    public Media() {
    }


    public Media(byte[] data ,String chemin, String type, int publication_id) {
        this.data = data;
        this.chemin = chemin;
        this.type = type;
        this.publication_id = publication_id;
    }

    public Media(byte[] data, int publication_id) {
        this.data = data;

        this.publication_id = publication_id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id = publication_id;
    }

    @Override
    public String toString() {
        return "Media{" +
                "chemin='" + chemin + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", publication_id=" + publication_id +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
