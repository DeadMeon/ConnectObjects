package fr.deadmeon.entity;

import fr.deadmeon.utils.KeyGenerator;

public class EntityArduinoMKRWIFI1010 extends ArduinoEntity {
    public EntityArduinoMKRWIFI1010(String key) {
        super(key, 14);
    }
    public EntityArduinoMKRWIFI1010() {
        super(new KeyGenerator().getKey(), 14);
    }
}
