package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import static utils.PathUtils.getOSLocalPath;


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
