package utils;

public class Utils {

    private Utils() {}

    public static String generateAccessToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
