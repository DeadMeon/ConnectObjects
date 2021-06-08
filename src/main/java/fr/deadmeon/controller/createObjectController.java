package fr.deadmeon.controller;

import fr.deadmeon.App;
import fr.deadmeon.entity.*;

import fr.deadmeon.request.TwitchIntegration.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class createObjectController implements Initializable {
    private static final String[] LIST_TYPES = {"Arduino"};
    private static final String[] LIST_CARTES = {EntityArduinoMKRWIFI1010.ARDUINO_NAME, ArduinoEntity.ARDUINO_NAME};

    @FXML
    private TextField name;
    @FXML
    private ChoiceBox types;
    @FXML
    private ChoiceBox cartes;
    @FXML
    private Spinner nbPin;
    @FXML
    private CheckBox IntegrationTwitchFollow;
    @FXML
    private CheckBox IntegrationTwitchSub;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        types.getItems().addAll(LIST_TYPES);
        cartes.getItems().addAll(LIST_CARTES);

        cartes.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> nbPin.setDisable(!LIST_CARTES[t1.intValue()].equals(ArduinoEntity.ARDUINO_NAME)));
        nbPin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 15 ));
        nbPin.setDisable(true);

        name.setOnMouseClicked(mouseEvent -> name.setStyle("-fx-border-color :transparent;"));
        types.setOnMouseClicked(mouseEvent -> types.setStyle("-fx-border-color :transparent;"));
        cartes.setOnMouseClicked(mouseEvent -> cartes.setStyle("-fx-border-color :transparent;"));
        nbPin.setOnMouseClicked(mouseEvent -> nbPin.setStyle("-fx-border-color :transparent;"));
    }

    @FXML

    private void onCreate() throws IOException {
        boolean isSafe = true;
        if (name.getText().equals("")
                || !name.getText().matches("\\G[a-zA-Z][a-zA-Z0-9]{0,25}\\Z")
                || App.getArduinoDataRecieveManager().getArduinoEntities().stream().anyMatch(x -> x.getName().equalsIgnoreCase(name.getText()))) {
            isSafe = false;
            name.setStyle("-fx-border-color :red;");
        }
        if (types.getValue() == null) {
            isSafe = false;
            types.setStyle("-fx-border-color :red;");
        }
        if (cartes.getValue() == null) {
            isSafe = false;
            cartes.setStyle("-fx-border-color :red;");
        }
        if (!nbPin.isDisable() && nbPin.getValue() == null) {
            isSafe = false;
            nbPin.setStyle("-fx-border-color :red;");
        }

        if (isSafe) {
            ArduinoEntity arduinoEntity;

            switch ((String) cartes.getValue()) {
                case EntityArduinoMKRWIFI1010.ARDUINO_NAME:
                    arduinoEntity = new EntityArduinoMKRWIFI1010(name.getText());
                    break;
                default:
                    arduinoEntity = new ArduinoEntity(name.getText(), (int) nbPin.getValue());
            }

            if (IntegrationTwitchFollow.isSelected()) {
                arduinoEntity.addOnWatch(Follow.OBJECT_TYPE);
            }
            if (IntegrationTwitchSub.isSelected()) {
                arduinoEntity.addOnWatch(Subscription.OBJECT_TYPE);
            }

            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();

            App.getArduinoDataRecieveManager().addEntity(arduinoEntity);
            App.getArduinoDataRecieveManager().save();
            App.setRoot("menuPrincipal");
        }
    }
}
