package fr.deadmeon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.deadmeon.entity.ArduinoEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileUtilities {
    public static final String LOCAL_SAVE_PATH = System.getenv("APPDATA") + "\\.ConnectObjects\\";
    public static final String LOCAL_BIN_SAVE_PATH = System.getenv("APPDATA") + "\\.ConnectObjects\\bin\\";
    public static final String DOCUMENT_PATH = System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH") + "\\Documents\\";

    public static String toHexString(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes()));
    }

    public static String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    public static <T> List<T> initWithFile(String path, List<T> t, Type tClass) {
        File file = new File(path);
        if (file.exists()) {
            return readJson(readInFile(path), tClass);
        } else {
            return t;
        }
    }

    public static <T> T initWithFile(String path, T t, Class<T> tClass) {
        File file = new File(path);
        if (file.exists()) {
            return readJson(readInFile(path), tClass);
        } else {
            return t;
        }
    }

    public static void writeInFile(String path, String str) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            System.err.println("Impossible de crée le ficher : \n" + path);
            ioException.printStackTrace();
        }

        try (FileWriter myWriter = new FileWriter(file) ){
            //myWriter.write(str);
            myWriter.write(toHexString(str));
        } catch (IOException e) {
            System.err.println("Imposible d'ecrire dans le ficher : \n" + path);
            e.printStackTrace();
        }
    }

    public static String readInFile(String path) {
        File file = new File(path);
        try (Scanner myReader = new Scanner(file)) {
            //return myReader.nextLine();
            return fromHexString(myReader.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("Imposible de lire le ficher : \n" + path);
            e.printStackTrace();
            return "";
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    public static String createJson(Object o) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(o);
    }

    public static <T> T readJson(String str, Class<T> tClass) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(str, tClass);
    }

    public static <T> T readJson(String str, Type tClass) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.fromJson(str, tClass);
    }
}
