package app;

import communication.SocketMessage;
import communication.SocketMessageType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {

    private static final Logger LOGGER = Logger.getLogger("MessageHandler");

    private static final int CORES_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * Many operations are I/O-bound, so it's reasonable to use CORES_COUNT*2 threads!
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(CORES_COUNT * 2);

    private static MessageHandler instance;
    private MessageHandler() {}
    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }

    /**
     * This function should handle the data received from a client. It should handle it by
     * checking the message category first.
     *
     * REQUESTS:      a request message requires an answer from the server to the client
     *
     * NOTIFICATIONS: a notification message does not require an answer from the server
     *
     * (Actually it's just for clarity, the server does not need to distinguish messages
     * with their category... The handling process is the same)
     * @param json the received data from the client
     * @param networkUser the user connection manager instance
     */
    public void handleMessage(String json, NetworkUser networkUser) {

        executorService.submit(() -> {

            SocketMessage message = SocketMessage.fromJson(json);
            SocketMessageType messageType = message.getSocketMessageType();

            if (messageType.getCategory() == SocketMessageType.SocketMessageCategory.REQUEST) {
                handleRequest(message, networkUser);
            } else if (messageType.getCategory() == SocketMessageType.SocketMessageCategory.NOTIFICATION) {
                handleNotification(message, networkUser);
            }
        });
    }

    /**
     * This function should handle a request message.
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleRequest(SocketMessage message, NetworkUser networkUser) {

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Received Request " + message.getSocketMessageType() + " from client (" + networkUser.getAddress() + ").");

        switch (message.getSocketMessageType()) {

            case LOGIN_USING_TOKEN_REQUEST -> handleLoginUsingTokenRequest(message, networkUser);
            case REGISTER_REQUEST -> handleRegisterRequest(message, networkUser);
            case LOGIN_REQUEST -> handleLoginRequest(message, networkUser);

            default -> LOGGER.warning("Received an invalid request from client (" + networkUser.getAddress() + ").");
        }
    }

    /**
     * This function should handle a token login request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleLoginUsingTokenRequest(SocketMessage message, NetworkUser networkUser) {
        //TODO
    }

    /**
     * This function should handle a login request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleLoginRequest(SocketMessage message, NetworkUser networkUser) {
        //TODO
    }

    /**
     * This function should handle a register request.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleRegisterRequest(SocketMessage message, NetworkUser networkUser) {
        //TODO
    }

    /**
     * This function should handle a notification message.
     *
     * @param message the parsed message
     * @param networkUser the user connection manager instance
     */
    private void handleNotification(SocketMessage message, NetworkUser networkUser) {
        //TODO
    }
}
