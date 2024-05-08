package services;

import entities.Biblio;
import entities.Ressource;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RessourceService {

    private Connection conx;

    public RessourceService() {
        conx = MyDB.getInstance().getConx();
    }

    public void ajouter(Ressource r) {
        String req = "INSERT INTO `ressource`(`biblio_id`, `titre_b`, `type_b`, `date_publica_b`, `categorie_resso_b`, `description_b`, `image_b_ressource`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setInt(1, r.getBiblio_id());
            ps.setString(2, r.getTitre_b());
            ps.setString(3, r.getType_b());
            ps.setDate(4, new java.sql.Date(r.getDate_publica_b().getTime()));
            ps.setString(5, r.getCategorie_resso_b());
            ps.setString(6, r.getDescription_b());
            ps.setString(7, r.getImage_b_ressource());
            ps.executeUpdate();
            System.out.println("Ressource ajoutée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void modifier(Ressource r) {
        String req = "UPDATE `ressource` SET `biblio_id`=?,`titre_b`=?,`type_b`=?,`date_publica_b`=?,`categorie_resso_b`=?,`description_b`=?,`image_b_ressource`=? WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(8, r.getId());
            pst.setInt(1, r.getBiblio_id());
            pst.setString(2, r.getTitre_b());
            pst.setString(3, r.getType_b());
            pst.setDate(4, new java.sql.Date(r.getDate_publica_b().getTime()));
            pst.setString(5, r.getCategorie_resso_b());
            pst.setString(6, r.getDescription_b());
            pst.setString(7, r.getImage_b_ressource());
            pst.executeUpdate();
            System.out.println("Ressource modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimer(int id) {
        String req = "DELETE FROM `ressource` WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Ressource supprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Ressource> afficher() {
        List<Ressource> list = new ArrayList<>();

        try {
            String req = "SELECT * FROM `ressource`";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Ressource(rs.getInt("id"), rs.getInt("biblio_id"),
                        rs.getString("titre_b"), rs.getString("type_b"),
                        rs.getDate("date_publica_b"), rs.getString("categorie_resso_b"),
                        rs.getString("description_b"), rs.getString("image_b_ressource")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public Ressource getById(int id) {
        Ressource ressource = null;
        String sql = "SELECT * FROM `ressource` WHERE `id`=?";
        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ressource = new Ressource(rs.getInt("id"), rs.getInt("biblio_id"),
                        rs.getString("titre_b"), rs.getString("type_b"),
                        rs.getDate("date_publica_b"), rs.getString("categorie_resso_b"),
                        rs.getString("description_b"), rs.getString("image_b_ressource"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return ressource;
    }

    public List<Ressource> getByBiblio(Biblio biblio) {
        List<Ressource> resources = new ArrayList<>();
        String sql = "SELECT * FROM ressource WHERE biblio_id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, biblio.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ressource ressource = new Ressource(rs.getInt("id"), rs.getInt("biblio_id"),
                        rs.getString("titre_b"), rs.getString("type_b"),
                        rs.getDate("date_publica_b"), rs.getString("categorie_resso_b"),
                        rs.getString("description_b"), rs.getString("image_b_ressource"));
                resources.add(ressource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }

}
