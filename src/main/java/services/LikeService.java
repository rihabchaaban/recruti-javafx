package services;

import services.IService1;
import models.Like;
import models.Media;

import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeService implements IService1<Like> {
    static Connection cnx = MyDatabase.getInstance().getConnection();
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
    public static void delete(String id) {
        String req = "Delete from  `like` where id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, Integer.valueOf(id));


            pst.executeUpdate();
            System.out.println("like deleted successfully");
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
    public static List<Like> getLikeListByPublicationId(int publicationId) {
        List<Like> likeList = new ArrayList<>();
        String sql = "SELECT * FROM `Like` WHERE publication_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, publicationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Like like = new  Like();
                    like.setId(resultSet.getInt("id"));
                    like.setUser_id(resultSet.getInt("user_id"));
                    like.setPublication_id(resultSet.getInt("publication_id"));
                    like.setDate_creation_like(resultSet.getTimestamp("date_creation_like"));

                    likeList.add(like);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des likes par ID de publication.", e);
        }
        return likeList;
    }

  //  @Override
    public Like getOne(int id) {
        return null;
    }
    public List<Integer> getTop3LikedPublicationIds() {
        List<Integer> top3LikedPublicationIds = new ArrayList<>();
        String sql = "SELECT publication_id, COUNT(*) AS like_count FROM `like` GROUP BY publication_id ORDER BY like_count DESC LIMIT 3";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int publicationId = resultSet.getInt("publication_id");
                    top3LikedPublicationIds.add(publicationId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des IDs des 3 publications les plus likées.", e);
        }
        return top3LikedPublicationIds;
    }

}
