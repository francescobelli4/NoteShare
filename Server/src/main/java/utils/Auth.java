package utils;

import java.util.Random;

public class Auth {

    private Auth() {}

    public static String generateAccessToken() {

        Random rnd = new Random(System.currentTimeMillis());
        return (String.valueOf(rnd.nextLong() * System.currentTimeMillis()));
    }

}
