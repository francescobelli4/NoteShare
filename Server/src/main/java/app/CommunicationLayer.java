package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class CommunicationLayer {

    private final Logger LOGGER = Logger.getLogger("Communication Layer");
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

        LOGGER.info("Server socket initialized and listening on port " + CommunicationLayer.getInstance().getServerSocket().getLocalPort() + "!");

        while (!server.isClosed()) {

            Socket client;

            try {
                client = this.server.accept();
            } catch (IOException ioException) {
                LOGGER.warning("A client could not connect to the server. Skipping...");
                continue;
            }

            LOGGER.info("A new client (" + client.getInetAddress().getHostAddress() + ") connected!");
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
