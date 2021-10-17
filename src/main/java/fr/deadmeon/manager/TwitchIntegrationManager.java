package fr.deadmeon.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deadmeon.data.TwitchIntegrationSave;
import fr.deadmeon.request.TwitchIntegration.*;
import fr.deadmeon.utils.FileUtilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TwitchIntegrationManager implements Runnable {
    private static final String CLIENT_ID = "cx8n4r6nnjkz98l1be5lkmm9lvv8xa";
    private static final String FILEPATH = FileUtilities.LOCAL_BIN_SAVE_PATH + "twitchIntegration.dat";
    private boolean isActive = false;

    private final ArduinoDataRecieveManager arduinoDataRecieveManager;
    private final TwitchIntegrationSave save;
    private String token; // = "n3r2vhq5286dmc6cdauwlo4fnza3mc";
    private String broadcaster_id;
    private Subscription subscription;
    private Follow follow;



    public TwitchIntegrationManager(ArduinoDataRecieveManager arduinoDataRecieveManager) {
        this.arduinoDataRecieveManager = arduinoDataRecieveManager;
        save = FileUtilities.initWithFile(
                FILEPATH,
                new TwitchIntegrationSave(null, new Subscription(), new Follow()),
                TwitchIntegrationSave.class);

        follow = save.getFollow();
        subscription = save.getSubscription();
        setToken(save.getToken());
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setToken(String token) {
        if (token != null) {
            this.token = token;
            try {
                String validateToken = validateToken();
                if (!validateToken.equals("Unauthorized 401")) {
                    broadcaster_id = validateToken;
                    setActive(true);
                } else {
                    this.token = null;
                }
            } catch (Exception e) {
                this.token = null;
                setActive(false);
            }
        }
    }

    public boolean isTokenSet() {
        return token != null;
    }



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
        http.setRequestProperty("Client-Id", CLIENT_ID);

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
            setToken(token);
        }

        http.disconnect();
    }

    public void runFollow() throws Exception {
        URL url = new URL("https://api.twitch.tv/helix/users/follows?to_id=" + broadcaster_id);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setRequestProperty("Client-Id", CLIENT_ID);

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
            setToken(token);
        }

        http.disconnect();
    }



    private void saveState() {
        save.setFollow(follow);
        save.setSubscription(subscription);
        save.setToken(token);
        FileUtilities.writeInFile(FILEPATH, FileUtilities.createJson(save));
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                if (isActive()) {
                    runSub();
                    runFollow();
                }
            }
        } catch (InterruptedException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            saveState();
        }

    }
}
