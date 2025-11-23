package services;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class TestServerCommunicationService {

    private ServerCommunicationService service;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    private ServerSocket serverSocket;
    private Socket acceptedSocket;

    private int findFreePort() {
        int port;
        try (ServerSocket tempSocket = new ServerSocket(0)) {
            port = tempSocket.getLocalPort();
        } catch (IOException e) {
            return -1;
        }

        return port;
    }

    /**
     * I need those latches to avoid socket initialization failures.
     * They are similar to semaphores in C...
     */
    private CountDownLatch serverReadyLatch;
    private CountDownLatch connectionEstablishedLatch;

    @Before
    public void setUp() throws Exception {
        int freePort = findFreePort();
        service = ServerCommunicationService.getInstance();

        serverReadyLatch = new CountDownLatch(1);
        connectionEstablishedLatch = new CountDownLatch(1);

        Thread t = new Thread(() -> {
            try {

                serverSocket = new ServerSocket(freePort);
                serverReadyLatch.countDown();

                acceptedSocket = serverSocket.accept();
                connectionEstablishedLatch.countDown();
            } catch (IOException e) {
                throw new RuntimeException("Server thread setup failed.", e);
            }
        });
        t.start();

        serverReadyLatch.await(5, TimeUnit.SECONDS);
        Socket socket = new Socket("localhost", freePort);
        connectionEstablishedLatch.await(5, TimeUnit.SECONDS);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        service.setSocket(socket);
        service.setStreamsForTest(dataInputStream, dataOutputStream);
    }

    @Test
    public void testSendAsync() throws Exception {

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");
        CompletableFuture<SocketMessage> future = service.sendAsync(testRequest);

        assertTrue(service.getPendingRequests().containsKey(testRequest.getSocketMessageID()));
    }

    @Test
    public void testSendAsyncFailure() throws Exception {

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");
        dataOutputStream.close();

        assertThrows(IOException.class, () -> service.sendAsync(testRequest));
    }

    @Test
    public void testSendSync() throws Exception {

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");

        Thread t = new Thread(() -> {

            while (!service.getPendingRequests().containsKey(testRequest.getSocketMessageID())) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            service.getPendingRequests().remove(testRequest.getSocketMessageID()).complete(testRequest);
        });
        t.start();

        SocketMessage response = service.sendSync(testRequest);

        assertFalse(service.getPendingRequests().containsKey(response.getSocketMessageID()));
    }

    @Test
    public void testSendSyncFailure() throws Exception {

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");

        dataOutputStream.close();

        assertThrows(IOException.class, ()-> service.sendSync(testRequest));
    }

    @Test
    public void testCloseCommunication() throws Exception {
        service.closeCommunication();

        assertTrue(service.getPendingRequests().isEmpty());
        assertTrue(service.getSocket().isClosed());
        assertThrows(IOException.class , () -> service.getDataInputStream().readUTF());
        assertThrows(IOException.class , () -> service.getDataOutputStream().writeUTF("aaa"));
    }

    @Test
    public void testRead() throws IOException {

        String testJson = SocketMessageFactory.createLoginRequest("abcd", "def").toJson();

        DataOutputStream out = new DataOutputStream(acceptedSocket.getOutputStream());
        out.writeUTF(testJson);

        service.read();

        assertEquals(0, service.getDataInputStream().available());
    }

    @Test
    public void testReadFailure() throws Exception {

        String testJson = SocketMessageFactory.createLoginRequest("abcd", "def").toJson();

        DataOutputStream out = new DataOutputStream(acceptedSocket.getOutputStream());
        out.writeUTF(testJson);

        dataInputStream.close();
        dataOutputStream.close();

        assertThrows(IOException.class, ()-> service.read());
    }
}
