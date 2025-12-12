package app;

import daos.user.NonPersistentUserDAO;
import daos.user.UserDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger("Server");

    private static UserDAO userDAO;

    private Server() {}

    /**
     * This function should set the app's options.
     * @param args the array of args provided at the startup
     * @throws IllegalArgumentException could not find value in enum
     */
    private static void parseArgs(String[] args) throws IllegalArgumentException {

        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of args: appMode");
        }

        Options.AppMode appMode = Options.AppMode.fromIdentifier(args[0]);
        Options.setAppMode(appMode);
    }

    /**
     * This function should initialize the right DAOs for this AppMode.
     */
    private static void initializeDAOs() {

        if (Options.getAppMode() == Options.AppMode.DEMO) {
            userDAO = new NonPersistentUserDAO();
        }
    }

    /**
     * This function should do all the operations needed to initialize the server.
     * @param args the arguments received at the startup
     */
    private static void initializeServer(String[] args) {

        try {
            parseArgs(args);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.severe("Error in parsing args: " + illegalArgumentException.getMessage());
            System.exit(-1);
        }

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Options set successfully");

        initializeDAOs();

        try {
            CommunicationLayer.getInstance().initializeServerSocket();
        } catch (IOException ioException) {
            LOGGER.severe("Could not initialize server socket! " + ioException.getMessage());
            System.exit(-1);
        }
    }

    static void main(String[] args) {
        initializeServer(args);
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static class Options {

        private static AppMode appMode = AppMode.DEMO;

        public static AppMode getAppMode() {
            return appMode;
        }

        public static void setAppMode(AppMode appMode) {
            Options.appMode = appMode;
        }

        public enum AppMode {
            DEMO("demo"),
            RELEASE("release");

            private final String identifier;

            AppMode(String identifier) {
                this.identifier = identifier;
            }

            public static AppMode fromIdentifier(String id) throws IllegalArgumentException {
                List<String> legalIdentifiers = new ArrayList<>();

                for (AppMode appMode : values()) {
                    legalIdentifiers.add(appMode.identifier);
                    if (appMode.identifier.equalsIgnoreCase(id)) {
                        return appMode;
                    }
                }
                throw new IllegalArgumentException("Invalid app mode: " + id + ". Use " + legalIdentifiers + " instead.");
            }
        }
    }
}
