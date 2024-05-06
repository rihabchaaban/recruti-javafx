package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Ban;
import tn.esprit.models.Publication;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.models.Ban;
public class BanService implements IService<Ban> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Ban ban) {
        String req = "INSERT INTO `ban`(`user_id`, `nb_post` ) VALUES (?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ban.getUser_id());
            pst.setInt(2, ban.getNb_post());
            pst.executeUpdate();
            System.out.println("ban added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Ban ban) {
        String req = "UPDATE `ban` SET `nb_post` = `nb_post` + 1 WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ban.getId());
            pst.executeUpdate();
            System.out.println("Ban updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDateBan(Ban ban) {
        String req = "UPDATE `ban` SET `date_ban` = CURRENT_TIMESTAMP WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ban.getId());
            pst.executeUpdate();
            System.out.println("Date_ban updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Ban ban) {
        String req = "DELETE FROM `ban` WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ban.getId());
            pst.executeUpdate();
            System.out.println("Ban deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Ban> getAll() {
        return null;
    }

    @Override
    public Ban getOne(int id) {
        return null;
    }

    public Ban getBanByUserId(int userId) {
        String req = "SELECT * FROM Ban WHERE user_id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, userId);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                Ban ban = new Ban();
                ban.setId(res.getInt("id"));
                ban.setNb_post(res.getInt("nb_post"));
                ban.setUser_id(res.getInt("user_id"));
                ban.setDate_ban(res.getTimestamp("date_ban"));
                return ban;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
