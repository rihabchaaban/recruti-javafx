package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Publication;
import models.PublicationModel;
import services.userService;

public class PostbackController  {
    @FXML
    private ImageView audience;

    @FXML
    private TextArea captionpub;

    @FXML
    private Text TextC;

    @FXML
    private Label date;

    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgVerified;

    @FXML
    private VBox pubContainer;

    @FXML
    private Label username;

    private int publicationId;
    public void initData(Publication publication) {
        userService u=new userService();
//        postIdTextField.setText(String.valueOf(publication.getId()));
//        userIdTextField.setText(String.valueOf(publication.getUserId()));
        captionpub.setText(publication.getContenu());
        username.setText(u.getUserById(publication.getUser_id()).getUsername());
       // TextC.setText(publication.getContenu());
        date.setText(String.valueOf(publication.getDate_creationpub()));

        this.publicationId = publication.getId();
    }
}
