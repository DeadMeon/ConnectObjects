package fr.deadmeon.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class FileUtilities {
    // Todo : faire la gestion des fichiers enregsitré sur la machine
    // Todo : enregistré les fichiers utile dans appdata (.json par exm) et le reste a coté de l'exec (.ion par exm)

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

    public static void saveInFile(String str, String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(myObj);
                myWriter.write(toHexString(str));
                myWriter.close();
            } else {
                Scanner myReader = new Scanner(myObj);
                str = fromHexString(myReader.nextLine());
                myReader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
