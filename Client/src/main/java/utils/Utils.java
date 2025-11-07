package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static utils.PathUtils.getOSLocalPath;


public class Utils {

    private Utils() {}

    public static void createUserSubfolders() {
        File dir = new File(getOSLocalPath() + "MyNotes");
        dir.mkdirs();
    }

    /**
     * This function should save the access token in a file
     * @param token
     */
    public static void saveAccessToken(String token) {

        try (FileWriter fileWriter = new FileWriter(getOSLocalPath() + "token.txt")) {
            fileWriter.write(token);
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

    //https://stackoverflow.com/questions/8248277/how-to-determine-if-a-string-has-non-alphanumeric-characters
    public static boolean isAlphanumeric(String str) {
        return str.matches("[a-zA-Z0-9]+") || str.isEmpty();
    }
}
