package services;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.responses.login.LoginFailureResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class TestServerCommunicationService {

    /**
     * Mocking a server
     */
    private ServerSocket server;

    @BeforeEach
    void mockServerSocket() throws IOException {
        server = new ServerSocket(0);

        Thread serverThread = new Thread(() -> {

            try {
                Socket client = server.accept();

                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());

                Thread readerThread = new Thread(() -> {
                    while (server != null && server.isBound() && !server.isClosed()) {

                        try {
                            System.out.println("READING....");
                            String data = dataInputStream.readUTF();
                            SocketMessage receivedMessage = SocketMessage.fromJson(data);

                            dataOutputStream.writeUTF(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_USERNAME, receivedMessage.getSocketMessageID()).toJson());
                        } catch (IOException _) {
                            break;
                        }
                    }
                });
                readerThread.start();
            } catch (IOException _) {
                Thread.currentThread().interrupt();
            }

        });
        serverThread.start();
    }

    @BeforeEach
    @AfterEach
    void reset() {
        ServerCommunicationService.reset();
    }

    /**
     * Testing if an instance is correctly delivered and also the difference
     * between two instances when the first one is reset.
     * Also testing that, without a reset, two instances are the same.
     */
    @Test
    void getInstance() {

        ServerCommunicationService instance = ServerCommunicationService.getInstance();
        assertNotNull(instance);

        ServerCommunicationService.reset();

        ServerCommunicationService newInstance = ServerCommunicationService.getInstance();
        assertNotNull(newInstance);
        assertNotEquals(instance, newInstance);

        ServerCommunicationService newInstance2 = ServerCommunicationService.getInstance();
        assertEquals(newInstance, newInstance2);
    }

    /**
     * This test should check if the socket is able to connect to a ServerSocket and
     * if the data streams are set correctly.
     */
    @Test
    void initializeConnection() throws IOException {

        ServerCommunicationService instance = ServerCommunicationService.getInstance();

        instance.initializeConnection("localhost", server.getLocalPort());

        assertNotNull(instance.getSocket());
        assertNotNull(instance.getDataInputStream());
        assertNotNull(instance.getDataOutputStream());
    }

    /**
     * This test should not test if the communication between client and server
     * works correctly. This should test is Futures and Requests work correctly.
     */
    @Test
    void sendAsync() throws ExecutionException, InterruptedException, IOException {

        ServerCommunicationService instance = ServerCommunicationService.getInstance();
        instance.initializeConnection("localhost", server.getLocalPort());

        SocketMessage mockedMessage = SocketMessageFactory.createLoginRequest("TMO", "IlKing");
        CompletableFuture<SocketMessage> messageFuture = instance.sendAsync(mockedMessage);

        CompletableFuture<Void> asyncTestFuture = new CompletableFuture<>();
        messageFuture.thenAccept(response -> {
            assertInstanceOf(LoginFailureResponseDTO.class, response.getPayload());
            asyncTestFuture.complete(null);
        });
        asyncTestFuture.get();

        assertFalse(instance.getPendingRequests().containsKey(mockedMessage.getSocketMessageID()));
    }

    @Test
    void sendSync() throws IOException, ExecutionException, InterruptedException {

        ServerCommunicationService instance = ServerCommunicationService.getInstance();
        instance.initializeConnection("localhost", server.getLocalPort());

        SocketMessage mockedMessage = SocketMessageFactory.createLoginRequest("TMO", "IlKing");
        SocketMessage receivedMessage = instance.sendSync(mockedMessage);

        assertInstanceOf(LoginFailureResponseDTO.class, receivedMessage.getPayload());
        assertFalse(instance.getPendingRequests().containsKey(mockedMessage.getSocketMessageID()));
    }

    @Test
    void closeCommunication() throws IOException {
        ServerCommunicationService instance = ServerCommunicationService.getInstance();
        instance.initializeConnection("localhost", server.getLocalPort());
        instance.closeCommunication();

        assertTrue(instance.getPendingRequests().isEmpty());
        assertThrows(IOException.class, () -> instance.getDataInputStream().readUTF());
        assertThrows(SocketException.class, () -> instance.getDataOutputStream().writeUTF(":D"));
        assertTrue(instance.getSocket().isClosed());
    }
}