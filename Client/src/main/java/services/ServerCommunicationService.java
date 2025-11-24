package services;

import communication.SocketMessage;
import communication.SocketMessageType;
import exceptions.UnrecognisedResponseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerCommunicationService {

    private static final Logger LOGGER = Logger.getLogger("ServerCommunicationService");

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final ConcurrentHashMap<String, CompletableFuture<SocketMessage>> pendingRequests = new ConcurrentHashMap<>();

    private static final int MAX_PARALLEL_THREADS = 10;
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
     */
    public CompletableFuture<SocketMessage> sendAsync(SocketMessage request) {

        CompletableFuture<SocketMessage> future = new CompletableFuture<>();

        executorService.submit(() -> {
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
            }
        });

        return future;
    }


    /**
     * This function should send a sync request to the server.
     * A sync request blocks the running thread until the answer is received from
     * the server.
     *
     * @param request the request that has to be sent
     * @return the SocketMessage received as response
     */
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
     * This function should close the communication socket with the server.
     */
    void closeCommunication() {
        try {
            this.pendingRequests.clear();

            if (this.dataInputStream != null) {
                this.dataInputStream.close();
            }

            if (this.dataOutputStream != null) {
                this.dataOutputStream.close();
            }

            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException cleanupException) {
            LOGGER.warning(String.format("Error during resource cleanup: %s", cleanupException.getMessage()));
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
                    read();
                } catch (EOFException eofException) {
                    LOGGER.severe(String.format("Connection closed from server: %s", eofException.getMessage()));
                    closeCommunication();
                } catch (IOException ioException) {
                    LOGGER.severe(String.format("Connection from server lost!: %s", ioException.getMessage()));
                    closeCommunication();
                }
            }
        });

        reader.start();
    }

    /**
     * This function should actually read from the socket.
     */
    void read() throws IOException{

        String data = this.dataInputStream.readUTF();

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info(String.format("RECEIVED %s", data));
        }

        executorService.submit(() -> {
            handleIncomingData(data);
        });
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
    void handleIncomingData(String data) throws UnrecognisedResponseException {
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
    void handleResponse(SocketMessage socketMessage) throws UnrecognisedResponseException {
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

    public ConcurrentMap<String, CompletableFuture<SocketMessage>> getPendingRequests() {
        return pendingRequests;
    }

    /**
     * TESTING
     */
    void setStreamsForTest(DataInputStream dis, DataOutputStream dos) {
        this.dataInputStream = dis;
        this.dataOutputStream = dos;
    }

    DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    Socket getSocket() {
        return socket;
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }
}
