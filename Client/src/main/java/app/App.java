package app;

import app.mvc.models.UserModel;
import views.colored.ColoredGraphicsController;
import javafx.application.Application;
import locales.Locales;
import persistency.nonpersistent.daos.NPFolderDAO;
import persistency.nonpersistent.daos.NPNoteDAO;
import persistency.persistent.daos.PFolderDAO;
import persistency.persistent.daos.PNoteDAO;
import persistency.shared.daos.FolderDAO;
import persistency.shared.daos.NoteDAO;
import utils.Utils;

import java.util.Objects;

public class App {

    private static boolean demoMode = false;

    private static NoteDAO noteDAO;
    private static FolderDAO folderDAO;

    public static void main(String[] args) {

        if (Objects.equals(args[0], "demo")) {
            System.out.println("Starting in DEMO mode...");
            demoMode = true;
        }
        Locales.initializeLocales();

        if (demoMode) {
            folderDAO = new NPFolderDAO();
            noteDAO = new NPNoteDAO();
        } else {
            folderDAO = new PFolderDAO();
            noteDAO = new PNoteDAO();
        }

        UserModel.getInstance().setActiveFolder(folderDAO.getRootFolder());
        Utils.createUserSubfolders();


        NetworkUser networkUser = NetworkUser.getInstance();
        networkUser.connect("localhost", 12345);

        // Now start the Graphics Controller!
        Application.launch(ColoredGraphicsController.class);
    }

    public static boolean getDemoMode() {
        return demoMode;
    }

    public static NoteDAO getNoteDAO() {
        return noteDAO;
    }

    public static FolderDAO getFolderDAO() {
        return folderDAO;
    }
}