package services;

import models.IService;
import models.Event;

import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;
import java.util.Map;


public class EventService implements IService <Event> {

    Connection cnx = MyDatabase.getInstance().getConnection();

    @Override
    public void add(Event event) {

        String req = "INSERT INTO `event`(`nom_e`, `date_e`, `heure_e`, `lieu_e`, `description`, `image_e`, `theme_e`, `cantact_e`) VALUES ('"+event.getNom_e()+"','"+event.getDate_e()+"','"+event.getHeure_e()+"','"+event.getLieu_e()+"','"+event.getDescription()+"','"+event.getImage_e()+"','"+event.getTheme_e()+"','"+event.getContact_e()+"')";

        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Event added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Event event) throws SQLException {
        String query = "UPDATE `event` SET nom_e = ?, date_e = ?, heure_e = ?, lieu_e = ?, description = ?, image_e = ?, theme_e = ?, cantact_e = ? WHERE id = ?";

        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setString(1, event.getNom_e());
        ps.setString(2, event.getDate_e());
        ps.setString(3, event.getHeure_e());
        ps.setString(4, event.getLieu_e());
        ps.setString(5, event.getDescription());
        ps.setString(6, event.getImage_e());
        ps.setString(7, event.getTheme_e());
        ps.setString(8, event.getContact_e());
        ps.setInt(9, event.getId());

        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Event updated successfully!");
        } else {
            System.out.println("Failed to update event. Event not found.");
        }
    }




    @Override
    public void delete(Event event) {
        String req = "DELETE FROM event WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, event.getId());
            ps.executeUpdate();
            System.out.println("Event deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM Event";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Event event = new Event();
                event.setId(res.getInt("id"));
                event.setNom_e(res.getString(2));
                event.setDate_e(res.getString(3));
                event.setHeure_e(res.getString(4));
                event.setLieu_e(res.getString(5));
                event.setDescription(res.getString(6));
                event.setImage_e(res.getString(7));
                event.setTheme_e(res.getString(8));
                event.setContact_e(res.getString(9));

                events.add(event);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return events;
    }

    public Event getById(int id) throws SQLException {
        Event event = null;
        String sql = "SELECT * FROM event WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                event = new Event(
                        rs.getInt("id"),
                        rs.getString("nom_e"),
                        rs.getString("description"),
                        rs.getString("theme_e"),
                        rs.getString("lieu_e"),
                        rs.getString("date_e"),
                        rs.getString("heure_e"),
                        rs.getString("cantact_e"), // Correction de la colonne contact_e
                        rs.getString("image_e")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    public List<Event> searchEvents(String searchCriteria) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event WHERE ";

        // Liste des colonnes dans la table event
        String[] columns = {"nom_e", "date_e", "heure_e", "lieu_e", "description", "image_e", "theme_e", "cantact_e"};

        // Construire la condition WHERE pour chaque colonne
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + " LIKE ?"; // Ajouter la colonne
            if (i < columns.length - 1) {
                query += " OR "; // Ajouter l'opérateur OR sauf pour la dernière colonne
            }
        }

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            // Remplir les paramètres pour chaque colonne
            for (int i = 0; i < columns.length; i++) {
                statement.setString(i + 1, "%" + searchCriteria + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setNom_e(resultSet.getString("nom_e"));
                event.setDate_e(resultSet.getString("date_e"));
                event.setHeure_e(resultSet.getString("heure_e"));
                event.setLieu_e(resultSet.getString("lieu_e"));
                event.setDescription(resultSet.getString("description"));
                event.setImage_e(resultSet.getString("image_e"));
                event.setTheme_e(resultSet.getString("theme_e"));
                event.setContact_e(resultSet.getString("cantact_e"));

                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    public List<Event> displaySorted(String sortBy) throws SQLException{
        String query = "SELECT id, nom_e, theme_e, cantact_e, date_e, heure_e, lieu_e, description, image_e FROM event ORDER BY ";

        switch (sortBy) {
            case "date_e":
                query += "date_e"; // Assuming startDate_event corresponds to date_e in your database
                break;
            case "nom_e":
                query += "nom_e"; // Assuming name_event corresponds to nom_e in your database
                break;
            case "lieu_e":
                query += "lieu_e"; // Assuming Space corresponds to lieu_e in your database
                break;
            default:
                throw new IllegalArgumentException("Invalid sort criteria.");
        }

        List<Event> events = new ArrayList<>();
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setNom_e(resultSet.getString("nom_e"));
                event.setDate_e(resultSet.getString("date_e"));
                event.setHeure_e(resultSet.getString("heure_e"));
                event.setLieu_e(resultSet.getString("lieu_e"));
                event.setDescription(resultSet.getString("description"));
                event.setImage_e(resultSet.getString("image_e"));
                event.setTheme_e(resultSet.getString("theme_e"));
                event.setContact_e(resultSet.getString("cantact_e")); // Corrected typo here

                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    public Map<String, Long> getEventTypeDistribution() throws SQLException {
        Map<String, Long> eventTypeDistribution = new HashMap<>();
        String query = "SELECT theme_e, COUNT(*) AS count FROM event GROUP BY theme_e";

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String eventType = resultSet.getString("theme_e");
                long count = resultSet.getLong("count");
                eventTypeDistribution.put(eventType, count);
            }
        }

        return eventTypeDistribution;
    }


}
