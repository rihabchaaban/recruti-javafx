package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class googlemapController {

    @FXML
    private WebView googleMapsWebView;

    @FXML
    private void initialize() {
        // Load Google Maps
        WebEngine webEngine = googleMapsWebView.getEngine();
        webEngine.loadContent("<iframe width=\"631\" height=\"448\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?width=631&amp;height=448&amp;hl=en&amp;q=%20Tunis+(map)&amp;t=&amp;z=12&amp;ie=UTF8&amp;iwloc=B&amp;output=embed\"></iframe> <a href='http://mapswebsite.net/fr'>MapsWebsite</a> <script type='text/javascript' src='https://embedmaps.com/google-maps-authorization/script.js?id=9fb8d55d50c5ca62a33f09dbeb9bb11b421e1b9e'></script>");
    }
}
