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

    @Override
    public Like getOne(int id) {
        return null;
    }
}
