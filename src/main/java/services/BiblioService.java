package services;

import entities.Biblio;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BiblioService {

    private Connection conx;

    public BiblioService() {
        conx = MyDB.getInstance().getConx();
    }

    public void ajouter(Biblio b) {
        String req = "INSERT INTO `biblio`(`nom_b`, `domaine_b`, `date_creation_b`, `image_b`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, b.getNom_b());
            ps.setString(2, b.getDomaine_b());
            ps.setDate(3, new java.sql.Date(b.getDate_creation_b().getTime()));
            ps.setString(4, b.getImage_b());
            ps.executeUpdate();
            System.out.println("Bibliothèque ajoutée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void modifier(Biblio b) {
        String req = "UPDATE `biblio` SET `nom_b`=?,`domaine_b`=?,`date_creation_b`=?,`image_b`=? WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(5, b.getId());
            pst.setString(1, b.getNom_b());
            pst.setString(2, b.getDomaine_b());
            pst.setDate(3, new java.sql.Date(b.getDate_creation_b().getTime()));
            pst.setString(4, b.getImage_b());
            pst.executeUpdate();
            System.out.println("Bibliothèque modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimer(int id) {
        String req = "DELETE FROM `biblio` WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Bibliothèque supprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Biblio> afficher() {
        List<Biblio> list = new ArrayList<>();

        try {
            String req = "SELECT * FROM `biblio`";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Biblio(rs.getInt("id"), rs.getString("nom_b"),
                        rs.getString("domaine_b"), rs.getDate("date_creation_b"),
                        rs.getString("image_b")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public Biblio getById(int id) {
        Biblio biblio = null;
        String sql = "SELECT * FROM `biblio` WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                biblio = new Biblio(rs.getInt("id"), rs.getString("nom_b"),
                        rs.getString("domaine_b"), rs.getDate("date_creation_b"),
                        rs.getString("image_b"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return biblio;
    }

    // Méthode pour récupérer toutes les bibliothèques disponibles
    public List<Biblio> getAllBiblios() {
        List<Biblio> list = new ArrayList<>();

        try {
            String req = "SELECT * FROM `biblio`";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Biblio(rs.getInt("id"), rs.getString("nom_b"),
                        rs.getString("domaine_b"), rs.getDate("date_creation_b"),
                        rs.getString("image_b")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

}
