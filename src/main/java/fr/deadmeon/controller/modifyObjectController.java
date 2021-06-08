package fr.deadmeon.controller;

import fr.deadmeon.App;
import fr.deadmeon.entity.ArduinoEntity;
import fr.deadmeon.request.TwitchIntegration.Follow;
import fr.deadmeon.request.TwitchIntegration.Subscription;
import fr.deadmeon.utils.ArduinoCodeGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class modifyObjectController implements Initializable {
    private ArduinoEntity arduino;
    private boolean isDeleteAlreadyClicked = false;

    @FXML
    private TextField textFieldSsidWifi;
    @FXML
    private TextField textFieldKeyArduino;
    @FXML
    private TextField textFieldKeyApp;
    @FXML
    private TextField textFieldNameArduino;
    @FXML
    private PasswordField passwordFieldMdpWifi;
    @FXML
    private CheckBox IntegrationTwitchFollow;
    @FXML
    private CheckBox IntegrationTwitchSub;
    @FXML
    private TextArea textAreaVariable;
    @FXML
    private TextArea textAreaSetup;
    @FXML
    private TextArea textAreaLoop;
    @FXML
    private Label labelMessage;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonCreateFile;
    @FXML
    private Button buttonSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IntegrationTwitchFollow.setText(IntegrationTwitchFollow.getText() + " (" + Follow.MESSAGE_TO_SEND + ")");
        IntegrationTwitchSub.setText(IntegrationTwitchSub.getText() + " (" + Subscription.MESSAGE_TO_SEND + ")");
        textFieldNameArduino.setOnMouseClicked(mouseEvent -> textFieldNameArduino.setStyle("-fx-border-color :transparent;"));
        textFieldKeyApp.setText(App.getArduinoDataRecieveManager().getKey());

        buttonDelete.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Attention : cette opération est irréversible.", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ?");
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(App.class.getResource("image/icon.png").toString()));
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                deleteArduino();
            }
        });
        buttonCreateFile.setOnMouseClicked(mouseEvent -> createFile());
        buttonSave.setOnMouseClicked(mouseEvent -> saveModification());
    }



    public void loadArduino(ArduinoEntity arduinoEntity) {
        this.arduino = arduinoEntity;
        textFieldNameArduino.setText(arduino.getName());
        textFieldKeyArduino.setText(arduino.getKey());
        textFieldSsidWifi.setText(arduino.getSsid());
        passwordFieldMdpWifi.setText(arduino.getMdp());

        textAreaVariable.setText(arduino.getVariable());
        textAreaSetup.setText(arduino.getSetup());
        textAreaLoop.setText(arduino.getLoop());

        IntegrationTwitchFollow.setSelected(arduino.containOnWatch(Follow.OBJECT_TYPE));
        IntegrationTwitchSub.setSelected(arduino.containOnWatch(Subscription.OBJECT_TYPE));
    }

    private void saveArduino() {
        arduino.setName(textFieldNameArduino.getText());
        arduino.setSsid(textFieldSsidWifi.getText());
        arduino.setMdp(passwordFieldMdpWifi.getText());

        arduino.setVariable(textAreaVariable.getText());
        arduino.setSetup(textAreaSetup.getText());
        arduino.setLoop(textAreaLoop.getText());

        arduino.setOnWatch(Follow.OBJECT_TYPE, IntegrationTwitchFollow.isSelected());
        arduino.setOnWatch(Subscription.OBJECT_TYPE, IntegrationTwitchSub.isSelected());
    }

    private boolean checkModification() {
        boolean isSafe = true;
        if (textFieldNameArduino.getText().equals("")) {
            isSafe = false;
            textFieldNameArduino.setStyle("-fx-border-color :red;");
            labelMessage.setText("Le nom de l'objet est vide.");
        } else if (!textFieldNameArduino.getText().matches("\\G[a-zA-Z][a-zA-Z0-9]{0,25}\\Z")) {
            isSafe = false;
            textFieldNameArduino.setStyle("-fx-border-color :red;");
            labelMessage.setText("Le nom de l'objet ne commence pas par une lettre ou est trop long (> 25 lettres).");
        } else if (App.getArduinoDataRecieveManager().getArduinoEntities().stream()
                .filter(x -> !arduino.equals(x))
                .anyMatch(x -> x.getName().equalsIgnoreCase(textFieldNameArduino.getText()))) {
            isSafe = false;
            textFieldNameArduino.setStyle("-fx-border-color :red;");
            labelMessage.setText("Le nom de l'objet existe déjà.");
        }
        return isSafe;
    }

    private void saveModification() {
        if (checkModification()) {
            saveArduino();
            App.getArduinoDataRecieveManager().save();
            goToMainMenu();
        }
    }

    private void createFile() {
        if (checkModification()) {
            saveArduino();
            App.getArduinoDataRecieveManager().save();
            new ArduinoCodeGenerator(arduino);
            goToMainMenu();
        }
    }

    private void deleteArduino() {
        App.getArduinoDataRecieveManager().removeEntity(arduino);
        App.getArduinoDataRecieveManager().save();
        goToMainMenu();
    }

    private void goToMainMenu() {
        try {
            App.setRoot("menuPrincipal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
