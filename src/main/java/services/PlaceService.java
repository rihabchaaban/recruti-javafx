package services;

import models.Place;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaceService implements IService2<Place> {
    private static Connection cnx = MyDatabase.getInstance().getConnection();

    @Override
    public void create(Place place) {
        String sql = "INSERT INTO place (gouvernement, ville, altitude, longitude) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, place.getGouvernement());
            ps.setString(2, place.getVille());
            ps.setFloat(3, place.getAltitude());
            ps.setFloat(4, place.getLongitude());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Place getById(int id) {
        Place place = null;
        String query = "SELECT * FROM place WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                place = new Place(
                        rs.getInt("id"),
                        rs.getString("gouvernement"),
                        rs.getString("ville"),
                        rs.getFloat("altitude"),
                        rs.getFloat("longitude")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return place;
    }

    @Override
    public List<Place> getAll() {
        List<Place> places = new ArrayList<>();
        String query = "SELECT * FROM place";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Place place = new Place(
                        rs.getInt("id"),
                        rs.getString("gouvernement"),
                        rs.getString("ville"),
                        rs.getFloat("altitude"),
                        rs.getFloat("longitude")
                );
                places.add(place);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return places;
    }

    @Override
    public void update(Place place) {
        String sql = "UPDATE place SET gouvernement = ?, ville = ?, altitude = ?, longitude = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, place.getGouvernement());
            ps.setString(2, place.getVille());
            ps.setFloat(3, place.getAltitude());
            ps.setFloat(4, place.getLongitude());
            ps.setInt(5, place.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM place WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
