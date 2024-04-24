package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Like;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeService implements IService<Like> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Like like) {
        String req = "INSERT INTO `like`( `publication_id`,`user_id`) VALUES ( ?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, like.getPublication_id());
            pst.setInt(2, like.getUser_id());
            pst.executeUpdate();
            System.out.println("Like added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public void update(Like like) {

    }

    @Override
    public void delete(Like like) {
        String req = "DELETE FROM `like` WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, like.getId());
            pst.executeUpdate();
            System.out.println("Like deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String req ="SELECT * FROM `Like`";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Like like = new Like();
                like.setId(res.getInt("id"));
                like.setUser_id(res.getInt("user_id"));
                like.setPublication_id(res.getInt("publication_id"));
                like.setDate_creation_like(res.getTimestamp("date_creation_like"));
                likes.add(like);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return likes;
    }
    public Like getLikeByIdPublicationAndIdUser(int publicationId, int userId) {
        String req = "SELECT * FROM `like` WHERE `publication_id` = ? AND `user_id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, publicationId);
            pst.setInt(2, userId);

            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Like like = new Like();
                    like.setId(res.getInt("id"));
                    like.setUser_id(res.getInt("user_id"));
                    like.setPublication_id(res.getInt("publication_id"));
                    like.setDate_creation_like(res.getTimestamp("date_creation_like"));
                    return like;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du like par ID de publication et ID d'utilisateur.", e);
        }

        return null;
    }
    public int getNumberOfLikesByIdPublication(int publicationId) {
        String req = "SELECT COUNT(*) FROM `like` WHERE `publication_id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, publicationId);

            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    return res.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du nombre de likes par ID de publication.", e);
        }

        return 0;
    }



    @Override
    public Like getOne(int id) {
        return null;
    }
}
