package entities;



public class Participation {
    private int id;
    private int event_id;
    private String role;
    private String Statut;
    private String feedback;
    private String nom_participant;

    public Participation() {
    }

    public Participation(int id, int event_id, String role, String statut, String feedback, String nom_participant) {
        this.id = id;
        this.event_id = event_id;
        this.role = role;
        Statut = statut;
        this.feedback = feedback;
        this.nom_participant = nom_participant;
    }

    public Participation(int event_id, String role, String statut, String feedback, String nom_participant) {
        this.event_id = event_id;
        this.role = role;
        Statut = statut;
        this.feedback = feedback;
        this.nom_participant = nom_participant;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getNom_participant() {
        return nom_participant;
    }

    public void setNom_participant(String nom_participant) {
        this.nom_participant = nom_participant;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", role='" + role + '\'' +
                ", Statut='" + Statut + '\'' +
                ", feedback='" + feedback + '\'' +
                ", nom_participant='" + nom_participant + '\'' +
                '}';
    }
}
