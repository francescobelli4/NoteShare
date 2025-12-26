package app;

import daos.message.MessageDAO;
import daos.message.NPMessageDAO;
import daos.user.NonPersistentUserDAO;
import daos.user.UserDAO;

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
    private MessageDAO messageDAO;
    private UserDAO userDAO;

    public Options getOptions() {
        return options;
    }

    /**
     * This function should set the app's options.
     * @param options the options object that was parsed from args
     * @throws IllegalArgumentException could not find value in enum
     */
    public void setOptions(Options options) throws IllegalArgumentException {
        this.options = options;

        if (options.getAppMode() == Options.AppMode.DEMO) {
            userDAO = new NonPersistentUserDAO();
            messageDAO = new NPMessageDAO();
        }
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public MessageDAO getMessageDAO() {
        return messageDAO;
    }

    /**
     * Testing
     */
    public static void reset() {
        instance = null;
    }
}
