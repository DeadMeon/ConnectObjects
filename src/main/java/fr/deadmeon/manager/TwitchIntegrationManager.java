package fr.deadmeon.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deadmeon.connectobjects.request.TwitchIntegration.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwitchIntegrationManager implements Runnable {


    private boolean isActive = true;
    private String client_id = "cx8n4r6nnjkz98l1be5lkmm9lvv8xa";
    private String token = "n3r2vhq5286dmc6cdauwlo4fnza3mc";
    private String broadcaster_id;
    private ArduinoDataRecieveManager arduinoDataRecieveManager;

    private Subscription subscription;
    private Follow follow;



    public TwitchIntegrationManager(ArduinoDataRecieveManager arduinoDataRecieveManager) {
        try {
            this.arduinoDataRecieveManager = arduinoDataRecieveManager;
            broadcaster_id = validateToken();
            follow = new Follow();
            subscription = new Subscription();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public Follow getFollow() {
        return follow;
    }




    // Todo : faire la partie connection via l'interface web et voir si possible de gardé le token dans l'interface web


    public String validateToken() throws Exception{
        URL url = new URL("https://id.twitch.tv/oauth2/validate");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + token);

        if (http.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Validate validate = gson.fromJson(reader.readLine(), Validate.class);

            http.disconnect();
            return validate.getUser_id();
        } else {
            http.disconnect();
            return "Unauthorized 401";
        }
    }

    public void runSub() throws Exception{
        URL url = new URL("https://api.twitch.tv/helix/subscriptions?broadcaster_id=" + broadcaster_id);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setRequestProperty("Client-Id", client_id);

        int responseCode = http.getResponseCode();

        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            BroadcasterSubscriptions bs = gson.fromJson(reader.readLine(), BroadcasterSubscriptions.class);

            if (bs.getData().length != 0) {
                if (!bs.getData()[0].equals(subscription)) {
                    System.out.println(bs);
                    subscription = bs.getData()[0];
                    arduinoDataRecieveManager.actualiser(subscription);
                }
            }
        } else {
            System.out.println(responseCode);
        }

        http.disconnect();
    }

    public void runFollow() throws Exception{
        URL url = new URL("https://api.twitch.tv/helix/users/follows?to_id=" + broadcaster_id);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setRequestProperty("Client-Id", client_id);

        int responseCode = http.getResponseCode();

        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            GsonBuilder builder = new GsonBuilder();

            builder.setPrettyPrinting();
            Gson gson = builder.create();
            BroadcasterFollows bf = gson.fromJson(reader.readLine(), BroadcasterFollows.class);

            if (bf.getData().length != 0) {
                if (!bf.getData()[0].equals(follow)) {
                    System.out.println(bf);
                    follow = bf.getData()[0];
                    arduinoDataRecieveManager.actualiser(follow);
                }
            }
        } else {
            System.out.println(responseCode);
        }

        http.disconnect();
    }


    @Override
    public void run() {
        while(isActive) {
            try {
                Thread.sleep(1000);
                runSub();
                runFollow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
