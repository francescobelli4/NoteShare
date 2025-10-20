package main;

import graphics.GraphicsController;
import locales.Locales;
import memory.nonpersistent.daos.NPNoteDAO;
import memory.nonpersistent.NPFolder;
import memory.shared.daos.NoteDAO;
import user.User;
import utils.Utils;

import java.util.Objects;

public class Main {

    public static boolean demoMode = false;
    public static NoteDAO noteDAO;
    //public static FolderDAO folderDAO;

    public static void main(String[] args) {

        if (Objects.equals(args[0], "demo")) {
            System.out.println("Starting in DEMO mode...");
            demoMode = true;
        }

        User user = User.getInstance();

        if (demoMode) {
            noteDAO = NPNoteDAO.getInstance();
            user.setRootFolder(new NPFolder());

            //folderDAO = NPFolderDAO.getInstance();
        } else {
            //userDAO = PUserDAO.getInstance();
        }

        user.setActiveFolder(user.getRootFolder());



        Utils.createUserSubfolders();

        user.connect("localhost", 12345);

        Locales.initializeLocales();

        // Now start the Graphics Controller!
        GraphicsController.setup();
    }
}