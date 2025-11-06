package app;

import app.mvc.BoundaryManager;
import app.mvc.models.FolderModel;
import app.mvc.models.UserModel;
import graphics.GraphicsController;
import graphics.colored.ColoredGraphicsController;
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

/**
 *
 * TODO BOUNDARY NOTIFICHE
 */
public class App {

    public static boolean demoMode = false;

    private static GraphicsController graphicsController;

    private static NoteDAO noteDAO;
    private static FolderDAO folderDAO;

    static void main(String[] args) {

        if (Objects.equals(args[0], "demo")) {
            System.out.println("Starting in DEMO mode...");
            demoMode = true;
        }
        Locales.initializeLocales();

        UserModel user = UserModel.getInstance();
        user.setActiveFolder(folderDAO.getRootFolder());
        Utils.createUserSubfolders();

        BoundaryManager boundaryManager = BoundaryManager.getInstance();
        boundaryManager.initializeLoginBoundary();
        boundaryManager.initializeRegisterBoundary();

        if (demoMode) {
            folderDAO = new NPFolderDAO();
            noteDAO = new NPNoteDAO();
        } else {
            folderDAO = new PFolderDAO();
            noteDAO = new PNoteDAO();
        }

        NetworkUser networkUser = NetworkUser.getInstance();
        networkUser.connect("localhost", 12345);

        // Now start the Graphics Controller!
        Application.launch(ColoredGraphicsController.class);
    }

    public static NoteDAO getNoteDAO() {
        return noteDAO;
    }

    public static FolderDAO getFolderDAO() {
        return folderDAO;
    }
}