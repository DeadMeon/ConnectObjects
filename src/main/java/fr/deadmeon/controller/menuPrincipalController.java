package fr.deadmeon.controller;

import fr.deadmeon.App;
import fr.deadmeon.entity.ArduinoEntity;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class menuPrincipalController implements Initializable {

    @FXML
    private FlowPane mainPanel;
    @FXML
    private Label entityName;
    @FXML
    private Label entityType;

    public void createArduino() {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(App.loadFXML("createObject"));
            scene.getStylesheets().add(App.loadCSS("createObject"));
            stage.setTitle("ConnectObjects");
            stage.setScene(scene);
            Image icon = new Image(App.class.getResource("image/icon.png").toString());
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnCloseRequest(windowEvent -> {

            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fade(Node node, double from, double to, double millis) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(node);
        fade.setDuration(Duration.millis(millis));
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.play();
    }

    @FXML
    private void goToOption() throws IOException {
        App.setRoot("option");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (ArduinoEntity arduino: App.getArduinoDataRecieveManager().getArduinoEntities()) {
            Button button = new Button();
            button.setPrefWidth(150);
            button.setPrefHeight(150);
            button.setId(arduino.getKey());
            button.setOnMouseEntered(mouseEvent -> {
                entityName.setText("Name : " + arduino.getName());
                entityType.setText("Type : " + arduino.getType());
                fade(entityName, 0,1,100);
                fade(entityType, 0,1,100);
            });
            button.setOnMouseExited(mouseEvent -> {
                fade(entityName, 1,0,100);
                fade(entityType, 1,0,100);
            });
            button.setOnMouseClicked(mouseEvent -> {
                try {
                    FXMLLoader fxmlLoader = App.setRoot("modifyObject", true);
                    modifyObjectController controller = fxmlLoader.getController();
                    controller.loadArduino(arduino);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ImageView imageView = new ImageView(arduino.getPathImage());
            if (imageView.getImage().getWidth() == 120 && imageView.getImage().getHeight() == 120) {
                button.setGraphic(imageView);
            } else {
                button.setGraphic(new ImageView(ArduinoEntity.ARDUINO_IMAGE));
            }
            mainPanel.getChildren().add(button);
        }
    }
}
