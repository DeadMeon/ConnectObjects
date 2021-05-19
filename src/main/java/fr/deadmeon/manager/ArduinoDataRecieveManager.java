package fr.deadmeon.manager;


import fr.deadmeon.connectobjects.entity.ArduinoEntity;
import fr.deadmeon.connectobjects.pattern.Observable;
import fr.deadmeon.connectobjects.pattern.Observateur;
import fr.deadmeon.connectobjects.utils.MulticastUDP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArduinoDataRecieveManager implements Runnable, Observateur {
    private final MulticastUDP multicast;
    private final List<ArduinoEntity> arduinoEntities = new ArrayList<>();

    public ArduinoDataRecieveManager(){
        multicast = new MulticastUDP();
        // TODO: If le fichier .json avec les objets existe alors le chargé
    }



    public List<ArduinoEntity> getArduinoEntities() {
        return arduinoEntities;
    }



    public boolean containEntity(ArduinoEntity entity) { return getArduinoEntities().contains(entity); }

    public void addEntity(ArduinoEntity entity) {
        if (!containEntity(entity))
            getArduinoEntities().add(entity);
    }

    public void removeEntity(ArduinoEntity entity) {
        if (containEntity(entity))
            getArduinoEntities().remove(entity);
    }

    public ArduinoEntity getEntity(String key) {
        return getArduinoEntities().stream()
                .filter(x -> x.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }



    @Override
    public void actualiser(Observable o) {
        arduinoEntities.stream()
                .filter(x -> x.containOnWatch(o.getObjectType()))
                .forEach(x -> {
                    try {
                        multicast.send(MulticastUDP.formatting(x.getKey(), o.getMessageToSend()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = multicast.receive();
                System.out.println("Received: " + received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        multicast.close();
        // Todo : Save la liste des arduinos enregistré dans la liste
    }
}
