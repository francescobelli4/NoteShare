package app;

import graphics.GraphicsController;
import graphics.colored.ColoredGraphicsController;
import javafx.application.Application;
import locales.Locales;
import persistency.nonpersistent.daos.NPNoteDAO;
import persistency.nonpersistent.NPFolder;
import persistency.shared.daos.NoteDAO;
import utils.Utils;

import java.util.Objects;

public class App {

    public static boolean demoMode = false;

    private static GraphicsController graphicsController;

    private static NoteDAO noteDAO;

    static void main(String[] args) {

        if (Objects.equals(args[0], "demo")) {
            System.out.println("Starting in DEMO mode...");
            demoMode = true;
        }

        User user = User.getInstance();

        if (demoMode) {
            noteDAO = NPNoteDAO.getInstance();
            user.setRootFolder(new NPFolder());
        } else {

        }

        Locales.initializeLocales();
        user.setActiveFolder(user.getRootFolder());
        Utils.createUserSubfolders();

        user.connect("localhost", 12345);

        // Now start the Graphics Controller!
        Application.launch(ColoredGraphicsController.class);
    }

    public static NoteDAO getNoteDAO() {
        return noteDAO;
    }
}