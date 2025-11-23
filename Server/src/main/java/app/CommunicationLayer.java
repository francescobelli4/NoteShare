package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class CommunicationLayer {

    private static final Logger LOGGER = Logger.getLogger("Communication Layer");
    private ServerSocket server;

    private static CommunicationLayer instance;
    private CommunicationLayer() {}
    public static CommunicationLayer getInstance() {
        if (instance == null) {
            instance = new CommunicationLayer();
        }
        return instance;
    }

    /**
     * This function should initialize the server socket and start listening for clients
     * @throws IOException client accept fails
     */
    public void initializeServerSocket() throws IOException {
        this.server = new ServerSocket(12345);

        if (!server.isClosed()) {
            LOGGER.info(String.format("Server socket initialized and listening on port %d!", server.getLocalPort()));
        }

        while (!server.isClosed()) {

            Socket client;

            try {
                client = this.server.accept();

                if (!client.isConnected())
                    continue;

            } catch (IOException ioException) {
                LOGGER.warning(String.format("A client could not connect to the server: %s. Skipping...", ioException.getMessage()));
                continue;
            }

            LOGGER.info(String.format("A new client (%s) connected!", client.getInetAddress().getHostAddress()));
            Thread t = new Thread(new NetworkUser(client));
            t.start();
        }

        LOGGER.severe("Server socket is closed!");
        System.exit(-1);
    }

    public ServerSocket getServerSocket() {
        return server;
    }
}
