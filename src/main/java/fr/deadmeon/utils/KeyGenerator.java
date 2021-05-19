package fr.deadmeon.utils;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class KeyGenerator {
    private final String FILE_NAME = ".rose.dat";
    private final int TARGET_KEY_LENGTH = 21;
    private String key;

    public KeyGenerator(int keyLength) {
      key = new SecureRandom().ints(48, 122 + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(keyLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public KeyGenerator() {
      key = new SecureRandom().ints(48, 122 + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(TARGET_KEY_LENGTH)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public String getKey() {
        return key;
    }
}
