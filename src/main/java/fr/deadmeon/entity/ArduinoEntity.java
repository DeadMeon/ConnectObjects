package fr.deadmeon.entity;

import java.util.ArrayList;

public class ArduinoEntity {
    private final String key;
    private final int nbPins;
    private ArrayList<String> onWatch;

    public ArduinoEntity(String key, int nbPins) {
        this.key = key;
        this.nbPins = nbPins;
        this.onWatch = new ArrayList<>();
    }

    public boolean isPinUsable(int pin) {
        return pin >= 0 && pin <= nbPins;
    }

    public String getKey() {
        return key;
    }



    public ArrayList<String> getOnWatch() {
        return this.onWatch;
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
}
