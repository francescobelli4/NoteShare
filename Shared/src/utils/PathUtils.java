package utils;

import java.io.File;

public class PathUtils {

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
}
