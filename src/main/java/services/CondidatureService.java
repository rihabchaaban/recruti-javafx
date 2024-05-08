package services;import entities.CrudCondidature;
import entities.Condidature;
import entities.Offer;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CondidatureService implements CrudCondidature<Condidature> {

    public Connection conx;
    public Statement stm;

    public CondidatureService() {
        conx = MyDB.getInstance().getConx();
    }

    @Override
    public void ajouter( Condidature c) {
        String req =
                "INSERT INTO condidature"
                        + "(nom_c,email_c,cv_c,lettre_mo,offer_id,user_id)"
                        + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, c.getNom_c());
            ps.setString(2, c.getEmail_c());
            ps.setString(3, c.getCv_c());
            ps.setString(4, c.getLettre_mo());
            ps.setInt(5, c.getOffer_id());
            ps.setInt(6, c.getUser_id());
            ps.executeUpdate();
            System.out.println("Condidature Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Condidature c) {
        String req = "UPDATE condidature "
                + "SET nom_c=?, email_c=?, cv_c=?, lettre_mo=?, offer_id=?," +
                "  user_id=? WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(7, c.getId());
            pst.setString(1, c.getNom_c());
            pst.setString(2, c.getEmail_c());
            pst.setString(3, c.getCv_c());
            pst.setString(4, c.getLettre_mo());
            pst.setInt(5, c.getOffer_id());
            pst.setInt(6, c.getUser_id());
            pst.executeUpdate();
            System.out.println("Offer modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM condidature WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Condidature suprimée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Condidature> afficher() {
        List<Condidature> list = new ArrayList<>();

        try {
            String req = "SELECT * from condidature";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Condidature(rs.getInt("id"), rs.getInt("offer_id"),
                        rs.getString("nom_c"), rs.getString("email_c"),
                        rs.getString("cv_c"), rs.getString("lettre_mo"),
                        rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Condidature getById(int id) throws SQLException {
        Condidature con = null;
        String sql = "SELECT * FROM condidature WHERE id = ?";
        try {
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                con=new Condidature(rs.getInt("id"), rs.getInt("offer_id"),
                        rs.getString("nom_c"), rs.getString("email_c"),
                        rs.getString("cv_c"), rs.getString("lettre_mo"),
                        rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public List<Condidature> getCondidaturesByOfferType(String offerType) {
        List<Condidature> allCondidatures = afficher(); // Supposons que cette méthode récupère toutes les candidatures
        List<Condidature> filteredCondidatures = new ArrayList<>();

        // Boucle à travers toutes les candidatures et ajoutez celles qui correspondent au type d'offre spécifié
        for (Condidature condidature : allCondidatures) {
            OfferService os = new OfferService();
            Offer offer = null;
            try {
                offer = os.getById(condidature.getOffer_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (offer != null && offer.getType_o().equals(offerType)) {
                filteredCondidatures.add(condidature);
            }
        }

        return filteredCondidatures;
    }
}
