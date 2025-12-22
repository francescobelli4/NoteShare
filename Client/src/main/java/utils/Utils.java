package utils;

import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Font;
import views.ViewNavigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Utils {

    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private Utils() {}

    public static void scaleFonts(Parent parent) {
        for (var node : parent.getChildrenUnmodifiable()) {

            if (node instanceof Parent p) {
                scaleFonts(p);
            }

            if (node instanceof Labeled l) {
                Font oldFont = l.getFont();
                l.setFont(Font.font(oldFont.getFamily(), ViewNavigator.scaleValue(oldFont.getSize())));
            }

            else if (node instanceof TextInputControl tic) {
                Font oldFont = tic.getFont();
                tic.setFont(Font.font(oldFont.getFamily(), ViewNavigator.scaleValue(oldFont.getSize())));
            }
        }
    }

    /**
     * This function should create a new directory
     * @param path the new directory's path
     */
    public static void createDir(String path) {
        File dir = new File(path);

        if (dir.exists()) {
            return;
        }

        if (dir.mkdirs() && logger.isLoggable(Level.INFO)) {
            logger.info("Folder in " + path + " created!");
        }
    }

    /**
     * This function should find a file from its path
     * @param path the file's path
     * @return the file or null if it's not found
     */
    public static File findFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file;
        }
        return null;
    }

    /**
     * This function should read a file content
     * @param file the file that has to be red
     * @return the content of the file
     * @throws IOException failed to read the file (does not exist?)
     */
    public static String readFile(File file) throws IOException {
        return Files.readString(Path.of(file.getAbsolutePath()));
    }

    /**
     * This function should save the access token in a file
     * @param token the received access token
     */
    public static void saveAccessToken(String token) {

        try (FileWriter fileWriter = new FileWriter(getOSLocalPath() + "access_token.txt")) {
            fileWriter.write(token);
        } catch (IOException _) {
            logger.severe("Failed writing access token to file");
        }
    }

    /**
     * This function returns a path to the app's local folder
     * @return the path to the app's local folder
     */
    public static String getOSLocalPath() {
        String path;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            path = System.getenv("APPDATA") + "\\NoteShare\\";
        } else {
            path = System.getProperty("user.home") + "/.local/share/NoteShare/";
        }

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
