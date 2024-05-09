package services;

import services.IService1;
import models.Media;
import models.Publication;

import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MediaService  implements IService1<Media> {
    static Connection cnx = MyDatabase.getInstance().getConnection();


    @Override
    public void add(Media media) {
        String req = "INSERT INTO `media`(`data`, `type`,`publication_id` , `chemin`) VALUES (?, ?, ? ,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setBytes(1, media.getData());
            pst.setString(2, media.getType()); // Set media type
            pst.setInt(3, media.getPublication_id());
            pst.setString(4, media.getChemin());
            pst.executeUpdate();
            System.out.println("media added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Media media) {

    }

    @Override
    public void delete(Media media) {

    }

    public static void delete(String id) {
        String req = "Delete from  `media` where id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, Integer.valueOf(id));


            pst.executeUpdate();
            System.out.println("media deleted successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Media> getAll() {
        List<Media> medias = new ArrayList<>();
        String req ="SELECT * FROM Media";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                Media media = new Media();
                media.setId(res.getInt("id"));
                media.setChemin(res.getString(2));
                media.setType(res.getString(3));
                media.setPublication_id(res.getInt("publication_id"));
                medias.add(media);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return medias;
    }
    public static List<Media> getMediaListByPublicationId(int publicationId) {
        List<Media> mediaList = new ArrayList<>();
        String sql = "SELECT * FROM Media WHERE publication_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, publicationId); // Définir le paramètre ID de la publication

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Media media = new Media();
                    media.setId(resultSet.getInt("id"));
                    media.setChemin(resultSet.getString("chemin"));
                    media.setType(resultSet.getString("type"));
                    media.setPublication_id(resultSet.getInt("publication_id"));
                    media.setData(resultSet.getBytes("data"));

                    mediaList.add(media);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des médias par ID de publication.", e);
        }

        return mediaList;
    }
 //   @Override
    public Media getOne(int id) {
        return null;
    }
}
