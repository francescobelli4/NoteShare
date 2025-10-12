package locales;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: EN and IT
 */
public class Locales {

    private static final Map<String, String> locales = new HashMap<>();

    public static void initializeLocales() {
        locales.put("register", "Register");
        locales.put("login", "Login");
    }

    public static String get(String key) {
        return locales.get(key);
    }
}
