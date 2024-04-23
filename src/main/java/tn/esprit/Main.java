package tn.esprit;

import tn.esprit.models.Commentaire;
import tn.esprit.models.Like;
import tn.esprit.models.Media;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.LikeService;
import tn.esprit.services.MediaService;
import tn.esprit.services.PublicationService;
import tn.esprit.util.MaConnexion;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {

//        PublicationService ps = new PublicationService();
        System.out.println(2);
        MediaService ps = new MediaService();
   //  Media media  = new Media("aaaaa","bbbbb",91);
   //     ps.add(media);
        System.out.println(ps.getMediaListByPublicationId(137));
       // LikeService cs = new LikeService();
//        Like like  = new Like(1,91);
//        cs.add(like);
       // System.out.println(cs.getAll());
      }
    }
