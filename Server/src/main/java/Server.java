import persistency.nonpersistent.daos.NPMessageDAO;
import persistency.nonpersistent.daos.NPUserDAO;
import persistency.persistent.daos.PUserDAO;
import persistency.shared.daos.MessageDAO;
import persistency.shared.daos.UserDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {

    private Server() {}

    /**
     * In demo mode, the app should use non-permanent memory ONLY!
     */
    private static boolean demoMode = false;

    private static UserDAO userDAO;
    private static MessageDAO messageDAO;

    static void main(String[] args) {

        if (Objects.equals(args[0], "demo")) {
            System.out.println("Starting in DEMO mode...");
            demoMode = true;
        }

        if (demoMode) {
            userDAO = NPUserDAO.getInstance();
            messageDAO = NPMessageDAO.getInstance();
        }

        setupServer(12345);
    }

    /**
     * This function should set up the server socket and start a new thread every time
     * a user connects.
     * Every NetworkUser thread will generate other 2 threads: one for reading and one for
     * writing to the client.
     * @param port the server communication port number
     */
    private static void setupServer(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server socket started up!\nWaiting for clients...");

            while (serverSocket.isBound()) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected!");

                Thread t = new Thread(new NetworkUser(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static MessageDAO getMessageDAO() {
        return messageDAO;
    }

    public static boolean getDemoMode() {
        return demoMode;
    }
}