package utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Hashing {

    private static final int HASHING_COST = 10;

    private Hashing() {}

    public static String hashString(String str) {
       return BCrypt.withDefaults().hashToString(HASHING_COST, str.toCharArray());
    }

    public static boolean verifyHash(String str, String hashedString) {
        return BCrypt.verifyer().verify(str.toCharArray(), hashedString.toCharArray()).verified;
    }
}
