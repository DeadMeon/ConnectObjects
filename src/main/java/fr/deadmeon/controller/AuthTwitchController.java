package fr.deadmeon.controller;


import fr.deadmeon.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthTwitchController implements Initializable {
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private String token;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        loadPage("id.twitch.tv/oauth2/authorize?client_id=cx8n4r6nnjkz98l1be5lkmm9lvv8xa&redirect_uri=http://localhost&response_type=token&scope=channel%3Aread%3Asubscriptions%20openid");
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            String lien = webEngine.getLocation();
            if (lien.contains("http://localhost/")) {
                token = lien.substring(lien.indexOf("#access_token=") + "#access_token=".length() , lien.indexOf("&scope="));
                update();
            }
        });
    }

    public void loadPage(String str){
        webEngine.load("https://" + str);
    }

    private void update() {
        Stage stage = (Stage) webView.getScene().getWindow();
        try {
            App.getTwitchIntegrationManager().setToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stage.close();
        }
    }
}

