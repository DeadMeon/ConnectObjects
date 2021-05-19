package fr.deadmeon;

import fr.deadmeon.connectobjects.entity.EntityArduinoMKRWIFI1010;
import fr.deadmeon.connectobjects.manager.ArduinoDataRecieveManager;
import fr.deadmeon.connectobjects.manager.TwitchIntegrationManager;
import fr.deadmeon.connectobjects.request.TwitchIntegration.Follow;

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
