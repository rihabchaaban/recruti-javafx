package pidevclient.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Place;
import entity.RendezVous;
import pidevclient.util.ConnectionDB;
import java.sql.Date;

public class RendezVousService implements IService<RendezVous> {
    private static Connection cnx = ConnectionDB.getInstance().getConn();

    @Override
    public void create(RendezVous r) {
        String sql = "INSERT INTO rendezvous(date_rendez, heure_rendez, email_condi, email_represen, id_place ) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, r.getDate_rendez());
            pstmt.setString(2, r.getHeure_rendez());
            pstmt.setString(3, r.getEmail_condi());
            pstmt.setString(4, r.getEmail_represen());
            pstmt.setInt(5, r.getId_place());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                r.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public RendezVous getById(int id) throws SQLException {
        RendezVous rendezVous = null;
        String query = "SELECT * FROM rendezvous WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rendezVous = new RendezVous(
                        rs.getInt("id"),
                        rs.getDate("date_rendez"),
                        rs.getString("heure_rendez"),
                        rs.getString("email_condi"),
                        rs.getString("email_represen"),
                        rs.getInt("id_place")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezVousService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rendezVous;
    }

    @Override
    public List<RendezVous> getAll() {
        List<RendezVous> liste_rendezvous = new ArrayList<>();
        try {
            String query = "SELECT * FROM rendezvous";
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste_rendezvous.add(new RendezVous(
                        rs.getInt("id"),
                        rs.getDate("date_rendez"),
                        rs.getString("heure_rendez"),
                        rs.getString("email_condi"),
                        rs.getString("email_represen"),
                        rs.getInt("id_place")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezVousService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste_rendezvous;
    }

    @Override
    public void update(RendezVous r) {
        String sql = "UPDATE rendezvous SET date_rendez = ?, heure_rendez = ?, email_condi = ?, email_represen = ?, id_place = ? WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setDate(1, r.getDate_rendez());
            pstmt.setString(2, r.getHeure_rendez());
            pstmt.setString(3, r.getEmail_condi());
            pstmt.setString(4, r.getEmail_represen());
            pstmt.setInt(5, r.getId_place());
            pstmt.setInt(6, r.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement pt = cnx.prepareStatement("DELETE FROM rendezvous WHERE id = ?");
            pt.setInt(1, id);
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RendezVousService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Place getPlaceByID(int id) {
        Place place = null;
        String query = "SELECT * FROM place WHERE id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                place = new Place();
                place.setId(rs.getInt("id"));
                place.setGouvernement(rs.getString("gouvernement"));
                place.setVille(rs.getString("ville"));
                place.setAltitude(rs.getFloat("altitude"));
                place.setLongitude(rs.getFloat("longitude"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RendezVousService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return place;
    }


    public static List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<>();
        String req = "SELECT * FROM place";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String gouvernement = rs.getString("gouvernement");
                String ville = rs.getString("ville");
                Float altitude = rs.getFloat("altitude");
                Float longitude = rs.getFloat("longitude");

                Place place = new Place(id,gouvernement, ville, altitude, longitude);
                placeList.add(place);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des services : " + e.getMessage());
        }
        return placeList;
    }



    }





