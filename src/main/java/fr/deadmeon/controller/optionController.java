package fr.deadmeon.controller;


import fr.deadmeon.App;
import fr.deadmeon.data.OptionSave;
import fr.deadmeon.utils.FileUtilities;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class optionController implements Initializable {
    public static final String FILEPATH = FileUtilities.LOCAL_SAVE_PATH + "option.json";
    private OptionSave optionSave;
    private SVGPath svgOn;
    private SVGPath svgOff;

    @FXML
    private ToggleButton toggleTwitchInteface;
    @FXML
    private TextField whereToSaveINO;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optionSave = FileUtilities.initWithFile(
                FILEPATH,
                new OptionSave(false, FileUtilities.DOCUMENT_PATH),
                OptionSave.class);

        svgOn = new SVGPath();
        svgOff = new SVGPath();
        svgOn.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zm0 8c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
        svgOff.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zM7 15c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");

        whereToSaveINO.setText(optionSave.getPathForArduinoFile());
        toggleTwitchInteface.setSelected(optionSave.isToggleTwitch());
        updateGraphicToggle(toggleTwitchInteface);
    }

    @FXML
    private void goToMenuPrincipal() throws IOException {
        saveOption();
        App.setRoot("menuPrincipal");
    }

    @FXML
    private void resetOption() {
        optionSave = new OptionSave(false, FileUtilities.DOCUMENT_PATH);
        whereToSaveINO.setText(optionSave.getPathForArduinoFile());
        toggleTwitchInteface.setSelected(optionSave.isToggleTwitch());
        updateGraphicToggle(toggleTwitchInteface);
    }

    private void updateGraphicToggle(ToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setGraphic(svgOn);
        } else {
            toggle.setGraphic(svgOff);
        }
    }

    public void onToggleTwitchInteface() {
        connectTwitchInteface();
        toggleTwitchInteface.setSelected(toggleTwitchInteface.isSelected());
        updateGraphicToggle(toggleTwitchInteface);
    }

    private void connectTwitchInteface(){
        if (!App.getTwitchIntegrationManager().isTokenSet()) {
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(App.loadFXML("AuthTwitch"));
                scene.getStylesheets().add(App.loadCSS("AuthTwitch"));
                stage.setTitle("ConnectObjects");
                stage.setScene(scene);

                Image icon = new Image(App.class.getResource("image/icon.png").toString());
                stage.getIcons().add(icon);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOnCloseRequest(windowEvent -> {
                    if (!App.getTwitchIntegrationManager().isTokenSet()){
                        toggleTwitchInteface.setSelected(false);
                        updateGraphicToggle(toggleTwitchInteface);
                    }
                });
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveOption() {
        App.getTwitchIntegrationManager().setActive(toggleTwitchInteface.isSelected());

        optionSave.setPathForArduinoFile(whereToSaveINO.getText());
        optionSave.setToggleTwitch(toggleTwitchInteface.isSelected());
        FileUtilities.writeInFile(FILEPATH, FileUtilities.createJson(optionSave));
    }
}
