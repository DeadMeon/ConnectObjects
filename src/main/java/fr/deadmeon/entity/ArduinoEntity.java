package fr.deadmeon.entity;

import fr.deadmeon.App;
import fr.deadmeon.utils.ArduinoCodeGenerator;
import fr.deadmeon.utils.KeyGenerator;

import java.util.ArrayList;

public class ArduinoEntity {
    public static final String ARDUINO_NAME = "Custom";
    public static final String ARDUINO_IMAGE = App.class.getResource("image/arduinoLogo.png").toString();

    private ArrayList<String> onWatch;
    private final String key;
    private int nbPins;
    private String pathImage;
    private String name;
    private String type;

    private String ssid;
    private String mdp;

    private String variable;
    private String setup;
    private String loop;

    public ArduinoEntity(String name, String type, String key, int nbPins, String pathImage) {
        this.name = name;
        this.type = type;
        this.key = key;
        this.nbPins = nbPins;
        this.pathImage = pathImage;
        this.onWatch = new ArrayList<>();

        this.variable = ArduinoCodeGenerator.ARDUINO_VAR;
        this.setup = ArduinoCodeGenerator.ARDUINO_SETUP;
        this.loop = ArduinoCodeGenerator.ARDUINO_LOOP;

        this.ssid = "";
        this.mdp = "";
    }

    public ArduinoEntity(String name, String key, int nbPins, String pathImage) {
        this(name, ARDUINO_NAME, key, nbPins, pathImage);
    }

    public ArduinoEntity(String name, String type, String key, int nbPins) {
        this(name, type, key, nbPins, ARDUINO_IMAGE);
    }

    public ArduinoEntity(String name, String key, int nbPins) {
        this(name, ARDUINO_NAME, key, nbPins, ARDUINO_IMAGE);
    }

    public ArduinoEntity(String name, int nbPins) {
        this(name, ARDUINO_NAME, KeyGenerator.generateKey(), nbPins, ARDUINO_IMAGE);
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getPathImage() {
        return pathImage;
    }

    public ArrayList<String> getOnWatch() {
        return this.onWatch;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getLoop() {
        return loop;
    }

    public void setLoop(String loop) {
        this.loop = loop;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean isPinUsable(int pin) {
        return pin >= 0 && pin <= nbPins;
    }

    public void setPin(int nbPins) {
        this.nbPins = nbPins;
    }

    public boolean containOnWatch(String str) {
        return getOnWatch().contains(str);
    }

    public void addOnWatch(String str) {
        if (!containOnWatch(str))
            getOnWatch().add(str);
    }

    public void removeOnWatch(String str) {
        if (containOnWatch(str))
            getOnWatch().remove(str);
    }

    public void setOnWatch(String str, boolean isInside) {
        if (isInside) {
            addOnWatch(str);
        } else {
            removeOnWatch(str);
        }
    }
}
