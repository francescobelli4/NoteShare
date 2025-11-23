package app;

import communication.SocketMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class NetworkUser implements Runnable {

    private final Logger LOGGER;

    private final String address;
    private final Socket user;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final Object writeLock = new Object();

    public NetworkUser(Socket user) {
        this.user = user;
        this.address = user.getInetAddress().getHostAddress();
        this.LOGGER = Logger.getLogger("Client (" + this.address + ")");
    }

    /**
     * This function should destroy this thread, the associated client and its structures
     * and data.
     */
    private void destroy() {

        LOGGER.info("Destroying client (" + address + ").");

        if (dataInputStream != null) {

            try {
                dataInputStream.close();
            } catch (IOException _) {}
            dataInputStream = null;
        }

        if (dataOutputStream != null) {

            try {
                dataOutputStream.close();
            } catch (IOException _) {}
            dataOutputStream = null;
        }

        if (user != null && user.isConnected()) {
            try {
                user.close();
            } catch (IOException _){}
        }

        Thread.currentThread().interrupt();
    }

    /**
     * This function should set up the communication with the user and start listening for
     * messages.
     */
    @Override
    public void run() {

        LOGGER.info("Thread for client (" + address + ") started!");

        try {
            setUpCommunication();
        } catch (IOException ioException) {
            LOGGER.severe("Could note get client (" + address + ") input/output stream. Closing connection.");
            destroy();
            return;
        }

        while (user.isConnected()) {
            read();
        }

        destroy();
    }

    /**
     * This function should set up the input and output streams
     * @throws IOException input or output stream can't be initialized
     */
    private void setUpCommunication() throws IOException {
        dataInputStream = new DataInputStream(user.getInputStream());
        dataOutputStream = new DataOutputStream(user.getOutputStream());
    }

    /**
     * This function should actually read the data from the input stream and call MessageHandler
     * to handle the message.
     */
    private void read() {
        try {
            String data = dataInputStream.readUTF();
            LOGGER.info("Received " + data + " from client (" + address + ").");

            MessageHandler.getInstance().handleMessage(data, this);
        } catch (IOException ioException) {
            LOGGER.severe("Connection with client (" + address + ") lost!");
            destroy();
        }
    }

    /**
     * This function should send a message to the client. It's thread safe, so many threads
     * can use it in parallel.
     * @param message the message that should be sent to the client.
     */
    public void write(SocketMessage message) {

        synchronized (writeLock) {

            try {
                dataOutputStream.writeUTF(message.toJson());
                dataOutputStream.flush();
            } catch (IOException ioException) {
                LOGGER.severe("Connection with client (" + address + ") lost!");
                destroy();
            }
        }
    }

    public String getAddress() {
        return address;
    }
}
