package app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger("Server");

    private Server() {}

    /**
     * This function should do all the operations needed to initialize the server.
     * @param args the arguments received at the startup
     */
    public static void initializeServer(String[] args) {

        try {
            AppContext.getInstance().setOptions(ArgsParser.parseArgs(args));

            if (LOGGER.isLoggable(Level.INFO))
                LOGGER.info("Options set successfully");
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.severe("Error in parsing args: " + illegalArgumentException.getMessage());
            System.exit(-1);
        }

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
}
