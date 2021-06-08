package fr.deadmeon.utils;

import java.security.SecureRandom;

public class KeyGenerator {
    private static final String FILEPATH = FileUtilities.LOCAL_BIN_SAVE_PATH + ".rose.dat";
    private static final int TARGET_KEY_LENGTH = 21;

    private String key;

    public KeyGenerator(int keyLength) {
        key = FileUtilities.initWithFile(
                FILEPATH,
                new SecureRandom().ints(48, 122 + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(keyLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString(),
                String.class);
        FileUtilities.writeInFile(FILEPATH, FileUtilities.createJson(key));
    }

    public KeyGenerator() {
        this(TARGET_KEY_LENGTH);
    }

    public String getKey() {
        return key;
    }

    public static String generateKey(int keyLength) {
        return new SecureRandom().ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(keyLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    public static String generateKey() {
        return generateKey(TARGET_KEY_LENGTH);
    }
}
