package app;

import communication.SocketMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkUser implements Runnable {

    private final Logger logger;

    private final String address;
    private final Socket user;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final Object writeLock = new Object();

    public NetworkUser(Socket user) {
        this.user = user;
        this.address = user.getInetAddress().getHostAddress();
        this.logger = Logger.getLogger(String.format("Client (%s)", this.address));
    }

    /**
     * This function should destroy this thread, the associated client and its structures
     * and data.
     */
    private void destroy() {

        if (logger.isLoggable(Level.INFO))
            logger.info(String.format("Destroying client (%s).", address));

        if (dataInputStream != null) {

            try {
                dataInputStream.close();
            } catch (IOException _) {
                // Not needed
            }
            dataInputStream = null;
        }

        if (dataOutputStream != null) {

            try {
                dataOutputStream.close();
            } catch (IOException _) {
                // Not needed
            }
            dataOutputStream = null;
        }

        if (user != null && user.isConnected()) {
            try {
                user.close();
            } catch (IOException _){
                // Not needed
            }
        }

        Thread.currentThread().interrupt();
    }

    /**
     * This function should set up the communication with the user and start listening for
     * messages.
     */
    @Override
    public void run() {

        if (logger.isLoggable(Level.INFO))
            logger.info(String.format("Thread for client (%s) started!", address));

        try {
            setUpCommunication();
        } catch (IOException ioException) {
            logger.severe(String.format("Could not get client (%s) input/output stream: %s. Closing connection.", address, ioException.getMessage()));
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
            MessageHandler.getInstance().handleMessage(data, this);
        } catch (IOException ioException) {
            logger.severe(String.format("Connection with client (%s) lost: %s", address, ioException.getMessage()));
            destroy();
        } catch (NullPointerException _) {
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
                logger.severe(String.format("Connection with client (%s) lost: %s.", address, ioException.getMessage()));
                destroy();
            }
        }
    }

    public String getAddress() {
        return address;
    }
}
