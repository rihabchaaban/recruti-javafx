package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Publication;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService implements IService<Commentaire> {
    static Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Commentaire commentaire) {
        String req = "INSERT INTO `commentaire`( `contenu_com`, `publication_id`,`user_id`) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, commentaire.getContenu_com());
            pst.setInt(2, commentaire.getPublication_id());
            pst.setInt(3, commentaire.getUser_id());

            pst.executeUpdate();
            System.out.println("comment added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Commentaire commentaire) {
        String req = "UPDATE `commentaire` SET `contenu_com` = ? WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, commentaire.getContenu_com());
            pst.setInt(2, commentaire.getId());
            pst.executeUpdate();
            System.out.println("Commentaire updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Commentaire commentaire) {
        String req = "DELETE FROM `commentaire` WHERE `id` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, commentaire.getId());
            pst.executeUpdate();
            System.out.println("Commentaire deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Commentaire> getAll() {
        List<Commentaire> commentaires = new ArrayList<>();
        String req ="SELECT * FROM Commentaire";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Commentaire commentaire = new Commentaire();
                commentaire.setId(res.getInt("id"));
                commentaire.setContenu_com(res.getString(2));
                commentaire.setUser_id(res.getInt("user_id"));
                commentaire.setPublication_id(res.getInt("publication_id"));
                commentaire.setDate_creation_com(res.getTimestamp("date_creation_com"));
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return commentaires;
    }
    public int getNumberOfCommentsByIdPublication(int publicationId) {
        String req = "SELECT COUNT(*) FROM `commentaire` WHERE `publication_id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, publicationId);

            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    return res.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du nombre de commentaire par ID de publication.", e);
        }

        return 0;
    }

    public static List<Commentaire> getCommentListByPublicationId(int publicationId) {
        List<Commentaire> commentaires = new ArrayList<>();
        String req = "SELECT * FROM Commentaire WHERE publication_id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, publicationId);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(res.getInt("id"));
                commentaire.setContenu_com(res.getString("contenu_com"));
                commentaire.setUser_id(res.getInt("user_id"));
                commentaire.setPublication_id(res.getInt("publication_id"));
                commentaire.setDate_creation_com(res.getTimestamp("date_creation_com"));
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commentaires par ID de publication.", e);
        }
        return commentaires;
    }

    @Override
    public Commentaire getOne(int id) {
        return null;
    }
}
