package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Publication;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements IService<Publication> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Publication publication) {
        String req = "INSERT INTO `publication`(`user_id_id`, `contenu`) VALUES (?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, publication.getUser_id());
            pst.setString(2, publication.getContenu());
            pst.executeUpdate();
            System.out.println("publication added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    // New method to add a publication and get its generated ID
    public int addAndGetId(Publication publication) {
        String req = "INSERT INTO `publication`(`user_id_id`, `contenu`) VALUES (?, ?)";
        int generatedId = -1; // Default value if no ID is generated

        try {
            PreparedStatement pst = cnx.prepareStatement(req, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, publication.getUser_id());
            pst.setString(2, publication.getContenu());
            pst.executeUpdate();
            System.out.println("Publication added successfully");

            // Retrieve the generated ID
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedId;
    }

    @Override
    public void update(Publication publication) {
        String req = "UPDATE `publication` SET `contenu` = ? WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, publication.getContenu());
            pst.setInt(2, publication.getId());
            pst.executeUpdate();
            System.out.println("Publication updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Publication publication) {
        String req = "DELETE FROM `publication` WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, publication.getId());
            pst.executeUpdate();
            System.out.println("Publication deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Publication> getAll() {
        List<Publication> publications = new ArrayList<>();
        String req ="SELECT * FROM Publication";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Publication publication = new Publication();
                publication.setId(res.getInt("id"));
                publication.setContenu(res.getString(3));
                publication.setUser_id(res.getInt("user_id_id"));
                publication.setDate_creationpub(res.getTimestamp("date_creationpub"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publications;
    }
    public List<Publication> getPublicationsByUserId(int userId) {
        List<Publication> publications = new ArrayList<>();
        String req = "SELECT * FROM Publication WHERE user_id_id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, userId);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Publication publication = new Publication();
                publication.setId(res.getInt("id"));
                publication.setContenu(res.getString("contenu"));
                publication.setUser_id(res.getInt("user_id_id"));
                publication.setDate_creationpub(res.getTimestamp("date_creationpub"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return publications;
    }

    @Override
    public Publication getOne(int id) {
        return null;
    }
}
