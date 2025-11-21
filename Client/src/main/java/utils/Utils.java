package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;


public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private Utils() {}

    public static void createUserSubfolders() {
        File dir = new File(getOSLocalPath() + "MyNotes");
        if (dir.mkdirs()) {
            logger.info("Subfolders created!");
        }
    }

    /**
     * This function should save the access token in a file
     * @param token
     */
    public static void saveAccessToken(String token) {

        try (FileWriter fileWriter = new FileWriter(getOSLocalPath() + "token.txt")) {
            fileWriter.write(token);
        } catch (IOException _) {
            logger.severe("Failed writing access token to file");
        }
    }

    /**
     * This function returns a path to the local app's folder
     * @return
     */
    public static String getOSLocalPath() {
        String path;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            path = System.getenv("APPDATA") + "\\NoteShare\\";
        } else {
            path = System.getProperty("user.home") + "/.local/share/NoteShare/";
        }

        File dir = new File(path);


        dir.mkdirs();

        return path;
    }

    //https://stackoverflow.com/questions/8248277/how-to-determine-if-a-string-has-non-alphanumeric-characters
    public static boolean isAlphanumeric(String str) {
        return str.matches("[a-zA-Z0-9]+") || str.isEmpty();
    }

    public static int getMaxPasswordLength() {
        return MAX_PASSWORD_LENGTH;
    }

    public static int getMaxUsernameLength() {
        return MAX_USERNAME_LENGTH;
    }

    public static int getMinPasswordLength() {
        return MIN_PASSWORD_LENGTH;
    }

    public static int getMinUsernameLength() {
        return MIN_USERNAME_LENGTH;
    }
}
