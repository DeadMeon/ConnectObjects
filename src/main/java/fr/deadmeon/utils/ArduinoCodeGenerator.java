package fr.deadmeon.utils;

import fr.deadmeon.App;
import fr.deadmeon.controller.optionController;
import fr.deadmeon.data.OptionSave;
import fr.deadmeon.entity.ArduinoEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArduinoCodeGenerator {
    public static final String ARDUINO_VAR = "const int ledPin = LED_BUILTIN;";
    public static final String ARDUINO_SETUP = "void initCode() {\n" +
            "\tpinMode(ledPin, OUTPUT);\n" +
            "}";
    public static final String ARDUINO_LOOP = "void runCode(char *msg) {\n" +
            "\tdigitalWrite(ledPin, HIGH);\t\t\t\t// sets the digital pin 13 on\n" +
            "\tdelay(5000);\t\t\t\t// waits for a second\n" +
            "\tdigitalWrite(ledPin, LOW);\n" +
            "}";

    public ArduinoCodeGenerator(ArduinoEntity arduinoEntity) {
        String path = FileUtilities.initWithFile(
                optionController.FILEPATH,
                new OptionSave(false, FileUtilities.DOCUMENT_PATH),
                OptionSave.class).getPathForArduinoFile();
        path += "\\" + arduinoEntity.getName() + "\\" ;

        String code = arduinoEntity.getVariable() + "\n\n"
                + readArduinoFile(App.class.getResource("arduino/arduino.ino").getPath()) + "\n\n"
                + arduinoEntity.getSetup() + "\n\n"
                + arduinoEntity.getLoop();

        String secret = "#define SECRET_SSID \"" + arduinoEntity.getSsid()
                + "\"\n#define SECRET_PASS \"" + arduinoEntity.getMdp()
                + "\"\n#define SECRET_KEY \"" + arduinoEntity.getKey()
                + "\"\n#define SECRET_SERV_KEY \"" + App.getArduinoDataRecieveManager().getKey() + "\"";

        createFile(path + arduinoEntity.getName() + ".ino", code);
        createFile(path + "arduino_secrets.h", secret);
    }



    public static void createFile(String path, String str) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            System.err.println("Impossible de crée le ficher : \n" + path);
            ioException.printStackTrace();
        }

        try (FileWriter myWriter = new FileWriter(file) ){
            myWriter.write(str);
        } catch (IOException e) {
            System.err.println("Imposible d'ecrire dans le ficher : \n" + path);
            e.printStackTrace();
        }
    }

    public static String readArduinoFile(String path) {
        File file = new File(path);
        String str = "";
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNext()){
                str += myReader.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.err.println("Imposible de lire le ficher : \n" + path);
            e.printStackTrace();
        }
        return str;
    }

}