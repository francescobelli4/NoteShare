package services;

import communication.SocketMessage;
import communication.SocketMessageType;
import exceptions.UnrecognisedResponseException;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ServerCommunicationService {

    private final Logger LOGGER = Logger.getLogger("ServerCommunicationService");

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final ConcurrentHashMap<String, CompletableFuture<SocketMessage>> pendingRequests = new ConcurrentHashMap<>();

    private final int MAX_PARALLEL_THREADS = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_PARALLEL_THREADS);
    private final Object writeLock = new Object();

    private static ServerCommunicationService instance;

    private ServerCommunicationService() {}

    public static ServerCommunicationService getInstance() {
        if (instance == null) {
            instance = new ServerCommunicationService();
        }

        return instance;
    }

    /**
     * This function should initialize the connection with the server
     * @throws IOException could not connect to the server
     */
    public void initializeConnection() throws IOException {
        this.socket = new Socket("localhost", 12345);

        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

        startReaderThread();
    }

    /**
     * This function should send an async request to the server.
     * An async request awaits an answer from the server, but it does not block the thread.
     * Even if this function should be quite fast, it's suggested to avoid running it in the
     * UI thread.
     *
     * @param request the request that has to be sent
     * @return a CompletableFuture<SocketMessage> that will be notified when the answer arrives
     * @throws IOException error writing to server socket
     */
    public CompletableFuture<SocketMessage> sendAsync(SocketMessage request) throws IOException {

        CompletableFuture<SocketMessage> future = new CompletableFuture<>();
        String data = request.toJson();

        try {

            synchronized (writeLock) {
                pendingRequests.put(request.getSocketMessageID(), future);
                dataOutputStream.writeUTF(data);
                dataOutputStream.flush();
            }
        } catch (IOException ioException) {
            if (pendingRequests.containsKey(request.getSocketMessageID()))
                pendingRequests.remove(request.getSocketMessageID()).completeExceptionally(ioException);
            throw ioException;
        }

        return future;
    }

    public SocketMessage sendSync(SocketMessage request) throws IOException, InterruptedException, ExecutionException {

        CompletableFuture<SocketMessage> future = new CompletableFuture<>();
        String data = request.toJson();

        try {

            synchronized (writeLock) {
                pendingRequests.put(request.getSocketMessageID(), future);
                dataOutputStream.writeUTF(data);
                dataOutputStream.flush();
            }

            return future.get();
        } catch (IOException | InterruptedException | ExecutionException ex) {
            if (pendingRequests.containsKey(request.getSocketMessageID()))
                pendingRequests.remove(request.getSocketMessageID()).completeExceptionally(ex);
            throw ex;
        }
    }

    /**
     * This function should start a thread that reads from the server.
     */
    private void startReaderThread() {

        Thread reader = new Thread(() -> {

            while (true) {
                assert socket != null;
                if (!socket.isConnected()) break;

                try {

                    String data = this.dataInputStream.readUTF();
                    LOGGER.info("RECEIVED " + data);
                    handleIncomingData(data);
                } catch (EOFException eofException) {
                    LOGGER.severe("Connection closed from server!");
                } catch (IOException ioException) {
                    LOGGER.severe("Connection from server lost!");
                } finally {

                    try {
                        if (this.dataInputStream != null) {
                            this.dataInputStream.close();
                        }
                        if (this.socket != null) {
                            this.socket.close();
                        }
                    } catch (IOException cleanupException) {
                        LOGGER.warning("Error during resource cleanup.");
                    }
                }
            }
        });

        reader.start();
    }

    /**
     * This function should handle the incoming data. The json incoming data is parsed to
     * create a SocketMessage and the SocketMessage handling is based on the SocketMessage Category.
     *
     * RESPONSE:     a Response message surely (if there are no unexpected errors) has a CompletableFuture
     *               waiting for that response. The handling logic should not be in this class, so I only
     *               notify the CompletableFuture that the response has arrived.
     *
     * NOTIFICATION: a Notification message does not have any CompletableFuture waiting for it. The handling
     *               is based on the SocketMessageType.
     *
     * @param data the received JSON string
     * @throws UnrecognisedResponseException there is no CompletableFuture waiting for that response
     */
    private void handleIncomingData(String data) throws UnrecognisedResponseException {
        SocketMessage receivedMessage = SocketMessage.fromJson(data);
        SocketMessageType socketMessageType = receivedMessage.getSocketMessageType();

        if (socketMessageType.getCategory() == SocketMessageType.SocketMessageCategory.RESPONSE) {
            handleResponse(receivedMessage);
        } else if (socketMessageType.getCategory() == SocketMessageType.SocketMessageCategory.NOTIFICATION) {
            handleNotification(receivedMessage);
        }
    }

    /**
     * This function should notify the respective CompletableFuture that the response has
     * arrived.
     *
     * @param socketMessage the received SocketMessage
     * @throws UnrecognisedResponseException there is no CompletableFuture waiting for that response
     */
    private void handleResponse(SocketMessage socketMessage) throws UnrecognisedResponseException {
        CompletableFuture<SocketMessage> future = pendingRequests.remove(socketMessage.getSocketMessageID());

        if (future != null) {
            future.complete(socketMessage);
        } else {
            throw new UnrecognisedResponseException(socketMessage.getSocketMessageID());
        }
    }

    private void handleNotification(SocketMessage socketMessage) {
        // TODO
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ConcurrentHashMap<String, CompletableFuture<SocketMessage>> getPendingRequests() {
        return pendingRequests;
    }
}
