package services;

import entities.CrudOffer;
import entities.Offer;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfferService implements CrudOffer<Offer> {

    public Connection conx;
    public Statement stm;

    public OfferService() {
        conx = MyDB.getInstance().getConx();
    }

    @Override
    public void ajouter(Offer o) {
        String req =
                "INSERT INTO offer"
                        + "(titre_o,description_o,type_o,localisation_o,date_o,dure_o,salarie_o,user_id)"
                        + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, o.getTitre_o());
            ps.setString(2, o.getDescription_o());
            ps.setString(3, o.getType_o());
            ps.setString(4, o.getLocalisation_o());
            ps.setDate(5, new java.sql.Date(o.getDate_o().getTime()));
            ps.setString(6, o.getDure_o());
            ps.setString(7, o.getSalaire_o());
            ps.setInt(8, o.getUser_id());
            ps.executeUpdate();
            System.out.println("Offer Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Offer o) {
        String req = "UPDATE offer "
                + "SET titre_o=?, description_o=?, type_o=?, localisation_o=?, date_o=?," +
                " dure_o=?, salarie_o=?, user_id=? WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(9, o.getId());
            pst.setString(1, o.getTitre_o());
            pst.setString(2, o.getDescription_o());
            pst.setString(3, o.getType_o());
            pst.setString(4, o.getLocalisation_o());
            pst.setDate(5, new java.sql.Date(o.getDate_o().getTime()));
            pst.setString(6, o.getDure_o());
            pst.setString(7, o.getSalaire_o());
            pst.setInt(8, o.getUser_id());
            pst.executeUpdate();
            System.out.println("Offer modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM offer WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Offer suprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Offer> afficher() {
        List<Offer> list = new ArrayList<>();

        try {
            String req = "SELECT * from offer";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Offer(rs.getInt("id"), rs.getString("titre_o"),
                        rs.getString("description_o"), rs.getString("type_o"),
                        rs.getString("localisation_o"), rs.getDate("date_o"),
                        rs.getString("dure_o"),rs.getString("salarie_o"),
                        rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Offer getById(int id) throws SQLException {
        Offer off = null;
        String sql = "SELECT * FROM offer WHERE id = ?";
        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                off=new Offer(rs.getInt("id"), rs.getString("titre_o"),
                        rs.getString("description_o"), rs.getString("type_o"),
                        rs.getString("localisation_o"), rs.getDate("date_o"),
                        rs.getString("dure_o"),rs.getString("salarie_o"),
                        rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return off;
    }
}
