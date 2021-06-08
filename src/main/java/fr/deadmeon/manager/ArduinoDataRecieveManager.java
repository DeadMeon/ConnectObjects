package fr.deadmeon.manager;

import com.google.gson.reflect.TypeToken;
import fr.deadmeon.entity.ArduinoEntity;
import fr.deadmeon.pattern.Observable;
import fr.deadmeon.pattern.Observateur;
import fr.deadmeon.utils.FileUtilities;
import fr.deadmeon.utils.KeyGenerator;
import fr.deadmeon.utils.MulticastUDP;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ArduinoDataRecieveManager implements Runnable, Observateur {
    private static final String FILEPATH = FileUtilities.LOCAL_BIN_SAVE_PATH + "arduinoManger.dat";

    private final MulticastUDP multicast;
    private final ArrayList<ArduinoEntity> arduinoEntities;
    private final String key;



    public ArduinoDataRecieveManager(){
        multicast = new MulticastUDP();
        Type arduinoEntitiesType = new TypeToken<ArrayList<ArduinoEntity>>(){}.getType();
        arduinoEntities = (ArrayList<ArduinoEntity>) FileUtilities.initWithFile(
                FILEPATH,
                new ArrayList<ArduinoEntity>(),
                arduinoEntitiesType);
        key = new KeyGenerator().getKey();
    }



    public List<ArduinoEntity> getArduinoEntities() {
        return arduinoEntities;
    }

    public String getKey() {
        return key;
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

    public void save() {
        if (arduinoEntities.isEmpty()) {
            FileUtilities.deleteFile(FILEPATH);
        } else {
            FileUtilities.writeInFile(FILEPATH, FileUtilities.createJson(arduinoEntities));
        }
    }



    @Override
    public void actualiser(Observable o) {
        arduinoEntities.stream()
                .filter(x -> x.containOnWatch(o.getObjectType()))
                .forEach(x -> {
                    try {
                        multicast.send(getKey(), x.getKey(), o.getMessageToSend());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String received = multicast.receive();
                    if (multicast.isToMe(getKey(), received))
                        System.out.println("Received: " + multicast.getData(received));
                } catch (SocketTimeoutException e) {
                    arduinoEntities.forEach(x -> {
                        try {
                            multicast.send(getKey(), x.getKey(),"SERVER_STILLCONNECTED");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void close() {
        multicast.close();
        save();
    }
}
