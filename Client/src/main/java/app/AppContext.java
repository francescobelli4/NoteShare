package app;

import daos.folder.FolderDAO;
import daos.folder.NPFolderDAO;
import daos.note.NPNoteDAO;
import daos.note.NoteDAO;
import exceptions.ArgsException;
import locales.Locales;
import models.user.UserModel;

public class AppContext {

    private AppContext() {}
    private static AppContext instance;
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    private Options options;
    private UserModel currentUser;
    private FolderDAO folderDAO;
    private NoteDAO noteDAO;

    public Options getOptions() {
        return options;
    }

    /**
     * This function should set the app's options.
     * @param options the options object that was parsed from args
     * @throws ArgsException wrong number of args or invalid argument
     * @throws IllegalArgumentException could not find value in enum
     */
    public void setOptions(Options options) throws ArgsException, IllegalArgumentException {
        this.options = options;

        if (options.getAppMode() == Options.AppMode.DEMO) {
            folderDAO = new NPFolderDAO();
            noteDAO = new NPNoteDAO();
        }

        if (options.getLanguage() == Options.Lang.EN) {
            Locales.initializeENLocales();
        } else {
            Locales.initializeITLocales();
        }
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public <T extends UserModel> T getCurrentUserAs(Class<T> type) {
        if (type.isInstance(currentUser)) {
            return type.cast(currentUser);
        }
        return null;
    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    public FolderDAO getFolderDAO() {
        return folderDAO;
    }

    public NoteDAO getNoteDAO() {
        return noteDAO;
    }
}
