package fr.deadmeon.entity;

import fr.deadmeon.App;
import fr.deadmeon.utils.KeyGenerator;

public class EntityArduinoMKRWIFI1010 extends ArduinoEntity {
    public static final int PIN = 14;
    public static final String ARDUINO_NAME = "MKR WIFI 1010";
    public static final String ARDUINO_IMAGE = App.class.getResource("image/arduinoMKRWIFI1010.png").toString();

    public EntityArduinoMKRWIFI1010(String name, String key, String pathImage) {
        super(name, ARDUINO_NAME, key, PIN, pathImage);
    }

    public EntityArduinoMKRWIFI1010(String name, String key) {
        super(name, ARDUINO_NAME, key, PIN, ARDUINO_IMAGE);
    }

    public EntityArduinoMKRWIFI1010(String name) {
        super(name, ARDUINO_NAME, KeyGenerator.generateKey(), PIN, ARDUINO_IMAGE);
    }
}
