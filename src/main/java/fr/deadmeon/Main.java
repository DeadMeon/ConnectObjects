package fr.deadmeon;

import fr.deadmeon.entity.EntityArduinoMKRWIFI1010;
import fr.deadmeon.manager.ArduinoDataRecieveManager;
import fr.deadmeon.manager.TwitchIntegrationManager;
import fr.deadmeon.request.TwitchIntegration.*;

public class Main {
    public static void main(String[] args) {
        ArduinoDataRecieveManager arduinoDataRecieveManager = new ArduinoDataRecieveManager();
        TwitchIntegrationManager twitchIntegrationManager = new TwitchIntegrationManager(arduinoDataRecieveManager);

        // Ajout des arduinos
        arduinoDataRecieveManager.addEntity(new EntityArduinoMKRWIFI1010("tnaIntxjMHrDxsRwOymLV"));
        arduinoDataRecieveManager.getEntity("tnaIntxjMHrDxsRwOymLV").addOnWatch(Follow.OBJECT_TYPE);

        new Thread(arduinoDataRecieveManager).start();
        new Thread(twitchIntegrationManager).start();
    }
}
