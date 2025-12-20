package models.user;

import app.App;
import communication.dtos.user.UserType;
import models.folder.FolderModel;

import java.util.ArrayList;

public class StudentUserModel extends UserModel {

    /** This should always be the app's local folder out of demo mode.
     *  In demo mode, the root folder (just like all the other folders) is saved on the
     *  stack.
     */
    private FolderModel rootFolder;

    /**
     * The folder that the user is actually working on. This can be a persistent one
     * or not. In general, this should contain the notes and subfolders that the user
     * needs to see.
     */
    private FolderModel activeFolder;
    private int coins;

    private final ArrayList<Listener> listeners = new ArrayList<>();

    public StudentUserModel() {
        setUserType(UserType.STUDENT);
    }

    public void addStudentListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        setRootFolder(App.getFolderDAO().getRootFolder());
        setActiveFolder(getRootFolder());

        if (loggedIn) {
            for (LoginListener l : super.getLoginListeners()) {
                l.onLoggedIn();
            }
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public FolderModel getActiveFolder() {
        return activeFolder;
    }

    public void setActiveFolder(FolderModel activeFolder) {
        this.activeFolder = activeFolder;

        for (Listener l : listeners) {
            l.activeFolderSet(activeFolder);
        }
    }

    public FolderModel getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(FolderModel rootFolder) {
        this.rootFolder = rootFolder;
    }

    public interface Listener {
        void activeFolderSet(FolderModel folder);
    }
}
