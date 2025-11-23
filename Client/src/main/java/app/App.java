package app;

import exceptions.ArgsException;
import locales.Locales;
import services.ServerCommunicationService;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger("App");

    /**
     * This function should set the app's options.
     * @param args the array of args provided at the startup
     * @throws ArgsException wrong number of args or invalid argument
     * @throws IllegalArgumentException could not find value in enum
     */
    static void parseArgs(String[] args) throws ArgsException, IllegalArgumentException {

        if (args.length < 3 || args.length > 4) {
            throw new ArgsException("Illegal number of args: appMode, language, uiType, [rootFolderPath]");
        }

        Options.AppMode appMode = Options.AppMode.fromIdentifier(args[0]);
        Options.Lang language = Options.Lang.fromIdentifier(args[1]);
        Options.UiType uiType = Options.UiType.fromIdentifier(args[2]);

        Options.setAppMode(appMode);
        Options.setLanguage(language);
        Options.setUiType(uiType);

        if (args.length == 4) {
            String rootFolderPath = args[3];
            Options.setRootFolderPath(rootFolderPath);
        }
    }

    /**
     * This function should create the app's local folder if it does not exist
     */
    private static void setupAppFolders() {
        Utils.createDir(Options.getRootFolderPath());
    }

    /**
     * This function should start the connection to server
     * @throws IOException connection goes wrong
     */
    private static void setupConnectionToServer() throws IOException {
        ServerCommunicationService.getInstance().initializeConnection();
    }

    /**
     * This function should set up the app by reading the Options
     * @param args the array of arguments passed to the app at the startup
     */
    static void initializeApp(String[] args) {
        try {
            parseArgs(args);
            LOGGER.info("Options set successfully");
            Locales.initializeLocales();
            LOGGER.info("Locales initialized");
            setupAppFolders();
            setupConnectionToServer();
            LOGGER.info("Connection to server established");

            Launcher.launchApp();
        } catch (ArgsException argsException) {
            LOGGER.severe(argsException.getMessage());
            System.exit(-1);
        } catch (IOException ioException) {
            LOGGER.severe(String.format("Connection to server failed: %s", ioException.getMessage()));
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        initializeApp(args);
    }

    public static class Options {

        private static AppMode appMode = AppMode.DEMO;
        private static Lang language = Lang.EN;
        private static UiType uiType = UiType.COLORED;
        private static String rootFolderPath = Utils.getOSLocalPath();

        public static AppMode getAppMode() {
            return appMode;
        }

        public static void setAppMode(AppMode appMode) {
            Options.appMode = appMode;
        }

        public static Lang getLanguage() {
            return language;
        }

        public static void setLanguage(Lang language) {
            Options.language = language;
        }

        public static UiType getUiType() {
            return uiType;
        }

        public static void setUiType(UiType uiType) {
            Options.uiType = uiType;
        }

        public static String getRootFolderPath() {
            return rootFolderPath;
        }

        public static void setRootFolderPath(String rootFolderPath) {
            Options.rootFolderPath = rootFolderPath;
        }

        public enum AppMode {
            DEMO("demo"),
            RELEASE("release");

            private final String identifier;

            AppMode(String identifier) {
                this.identifier = identifier;
            }

            public static AppMode fromIdentifier(String id) throws ArgsException {
                List<String> legalIdentifiers = new ArrayList<>();

                for (AppMode appMode : values()) {
                    legalIdentifiers.add(appMode.identifier);
                    if (appMode.identifier.equalsIgnoreCase(id)) {
                        return appMode;
                    }
                }
                throw new ArgsException("Invalid app mode: " + id + ". Use " + legalIdentifiers + " instead.");
            }
        }

        public enum Lang {
            IT("it"),
            EN("en");

            private final String identifier;

            Lang(String identifier) {
                this.identifier = identifier;
            }

            public static Lang fromIdentifier(String id) throws ArgsException {
                List<String> legalIdentifiers = new ArrayList<>();

                for (Lang lang : values()) {
                    legalIdentifiers.add(lang.identifier);
                    if (lang.identifier.equalsIgnoreCase(id)) {
                        return lang;
                    }
                }
                throw new ArgsException("Invalid lang: " + id + ". Use " + legalIdentifiers + " instead.");
            }
        }

        public enum UiType {
            COLORED("colored"),
            OLD("old");

            private final String identifier;

            UiType(String identifier) {
                this.identifier = identifier;
            }

            public static UiType fromIdentifier(String id) throws ArgsException {
                List<String> legalIdentifiers = new ArrayList<>();

                for (UiType uiType : values()) {
                    legalIdentifiers.add(uiType.identifier);
                    if (uiType.identifier.equalsIgnoreCase(id)) {
                        return uiType;
                    }
                }
                throw new ArgsException("Invalid ui type: " + id + ". Use " + legalIdentifiers + " instead.");
            }
        }
    }
}
