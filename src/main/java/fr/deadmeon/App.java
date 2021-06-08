package fr.deadmeon;


import fr.deadmeon.manager.ArduinoDataRecieveManager;
import fr.deadmeon.manager.TwitchIntegrationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static ArduinoDataRecieveManager arduinoDataRecieveManager;
    private static TwitchIntegrationManager twitchIntegrationManager;
    private static final ArrayList<Thread> threads = new ArrayList<>();



    public static ArduinoDataRecieveManager getArduinoDataRecieveManager() {
        return arduinoDataRecieveManager;
    }

    public static TwitchIntegrationManager getTwitchIntegrationManager() {
        return twitchIntegrationManager;
    }



    @Override
    public void start(Stage stage) throws IOException {
        arduinoDataRecieveManager = new ArduinoDataRecieveManager();

        Image icon = new Image(App.class.getResource("image/icon.png").toString());

        scene = new Scene(loadFXML("menuPrincipal"));
        scene.getStylesheets().add(loadCSS("menuPrincipal"));

        stage.getIcons().add(icon);
        stage.setTitle("ConnectObjects");
        stage.setScene(scene);

        stage.show();

        twitchIntegrationManager = new TwitchIntegrationManager(arduinoDataRecieveManager);

        threads.add(new Thread(arduinoDataRecieveManager));
        threads.add(new Thread(twitchIntegrationManager));
        threads.forEach(Thread::start);

        stage.setOnCloseRequest(windowEvent -> {threads.forEach(Thread::interrupt);});
    }



    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getStylesheets().clear();
        scene.getStylesheets().add(0,loadCSS(fxml));
    }

    public static FXMLLoader setRoot(String fxml, boolean returnLoader) throws IOException {
        FXMLLoader fxmlLoader = loadFXML(fxml, returnLoader);
        scene.setRoot(fxmlLoader.load());
        scene.getStylesheets().clear();
        scene.getStylesheets().add(0,loadCSS(fxml));
        return fxmlLoader;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static FXMLLoader loadFXML(String fxml, boolean returnLoader) throws IOException {
        return new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
    }

    public static String loadCSS(String css) {
        return Objects.requireNonNull(App.class.getResource("css/" + css + ".css")).toExternalForm();
    }



    public static void main(String[] args) {
        launch();
    }

}