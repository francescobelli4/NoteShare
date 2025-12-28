package app;

import exceptions.ArgsException;
import models.user.StudentUserModel;
import services.ServerCommunicationService;
import utils.PDFToImage;
import utils.Utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger("App");

    /**
     * This function should set up the app.
     * @param args the array of arguments passed to the app at the startup
     */
    public static void initializeApp(String[] args) {

        try {
            AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));
            if (LOGGER.isLoggable(Level.INFO))
                LOGGER.info("Options set successfully");

            Utils.createDir(AppContext.getInstance().getOptions().getRootFolderPath());
            ServerCommunicationService.getInstance().initializeConnection("localhost", 12345);
            // When a user is not logged in, it's classified as a student
            AppContext.getInstance().setCurrentUser(new StudentUserModel());

            PDFToImage.initialize();

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
}
