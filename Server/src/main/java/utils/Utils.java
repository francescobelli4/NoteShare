package utils;

import java.util.Random;

public class Utils {

    private Utils() {}

    public static String generateAccessToken() {

        Random rnd = new Random(System.currentTimeMillis());
        return (String.valueOf(rnd.nextLong() * System.currentTimeMillis()));
    }
}
