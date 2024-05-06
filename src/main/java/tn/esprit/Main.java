package tn.esprit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.OkHttpClient;
import tn.esprit.models.Commentaire;
import tn.esprit.models.Like;
import tn.esprit.models.Media;
import tn.esprit.models.Publication;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.LikeService;
import tn.esprit.services.MediaService;
import tn.esprit.services.PublicationService;
import tn.esprit.util.MaConnexion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String inputText = "love the bitch ass pussy";

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = String.format("api_key=b96a1ba195ec2ef8f24ab29a30ff32eb&method=webpurify.live.replace&text=%s&replacesymbol=*&lang=en&format=json", inputText);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // Construction de la requête
        Request request = new Request.Builder()
                .url("https://api1.webpurify.com/services/rest/")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String extractedText = jsonNode.get("rsp").get("text").asText();

        System.out.println("Texte extrait : " + extractedText);


//        PublicationService ps = new PublicationService();
//        System.out.println(ps.getAll());
      //  MediaService ps = new MediaService();
   //  Media media  = new Media("aaaaa","bbbbb",91);
   //     ps.add(media);
     //   System.out.println(ps.getMediaListByPublicationId(137));
       // LikeService cs = new LikeService();
//        Like like  = new Like(1,91);
//        cs.add(like);
       // System.out.println(cs.getAll());
      }
    }
