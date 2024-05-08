package services;

import entities.Event;
import entities.IService;
import entities.Participation;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.SQLException;
import java.util.Map;


public class ParticipationService implements IService <Participation> {

    Connection cnx = MyDB.getInstance().getConx();

    @Override
    public void add(Participation participation) {
        String req = "INSERT INTO participation (event_id, role, Statut, feedback, nom_participant) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, participation.getEvent_id());
            ps.setString(2, participation.getRole());
            ps.setString(3, participation.getStatut());
            ps.setString(4, participation.getFeedback());
            ps.setString(5, participation.getNom_participant());

            ps.executeUpdate();
            System.out.println("Participation added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Participation participation) throws SQLException {
        String query = "UPDATE participation SET role = ?, Statut = ?, feedback = ?, nom_participant = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, participation.getRole());
            ps.setString(2, participation.getStatut());
            ps.setString(3, participation.getFeedback());
            ps.setString(4, participation.getNom_participant());
            ps.setInt(5, participation.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Participation updated successfully!");
            } else {
                System.out.println("Failed to update participation. Participation not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Participation participation) {
        String req = "DELETE FROM participation WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, participation.getId());
            ps.executeUpdate();
            System.out.println("Participation deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Participation> getAll() {

        List<Participation> participations = new ArrayList<>();
        String req = "SELECT * FROM participation";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Participation participation = new Participation();
                participation.setId(res.getInt("id"));
                participation.setEvent_id(res.getInt("event_id"));
                participation.setRole(res.getString("role"));
                participation.setStatut(res.getString("Statut"));
                participation.setFeedback(res.getString("feedback"));
                participation.setNom_participant(res.getString("nom_participant"));

                participations.add(participation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return participations;
    }

    public Participation getById(int id) throws SQLException {
        Participation participation = null;
        String sql = "SELECT * FROM participation WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                participation = new Participation(
                        rs.getInt("id"),
                        rs.getInt("event_id"),
                        rs.getString("role"),
                        rs.getString("Statut"),
                        rs.getString("feedback"),
                        rs.getString("nom_participant")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participation;
    }
    public Map<Integer, Long> getParticipationCountPerEvent() throws SQLException {
        Map<Integer, Long> participationCountPerEvent = new HashMap<>();
        String query = "SELECT event_id, COUNT(*) AS count FROM participation GROUP BY event_id";

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int eventId = resultSet.getInt("event_id");
                long count = resultSet.getLong("count");
                participationCountPerEvent.put(eventId, count);
            }
        }

        return participationCountPerEvent;
    }
    public long getParticipationCountByEventId(int eventId) {
        long count = 0;
        String query = "SELECT COUNT(*) AS count FROM participation WHERE event_id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }



}
