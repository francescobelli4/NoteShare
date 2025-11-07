package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import static utils.PathUtils.getOSLocalPath;


public class Utils {

    public static void createUserSubfolders() {
        File dir = new File(getOSLocalPath() + "MyNotes");
        dir.mkdirs();
    }

    /**
     * This function should save the access token in a file
     * @param token
     */
    public static void saveAccessToken(String token) {

        File tokenFile = new File(getOSLocalPath() + "token.txt");

        try {
            tokenFile.createNewFile();

            FileWriter fileWriter = new FileWriter(getOSLocalPath() + "token.txt");
            fileWriter.write(token);
            fileWriter.close();
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
