package services;

import com.google.gson.Gson;
import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import exceptions.UnrecognisedResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class TestServerCommunicationService {

    private ServerCommunicationService service;
    private DataOutputStream dataOutputStream;
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private DataInputStream dataInputStream;
    private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());


    @Before
    public void setUp() {
        // Getting an instance of ServerCommunicationService
        service = ServerCommunicationService.getInstance();

        // Setting a mocked outputStream in service
        dataOutputStream = new DataOutputStream(OutputStream.nullOutputStream());
        dataInputStream = new DataInputStream(byteArrayInputStream);

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

        dataOutputStream.close();

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");
        Assert.assertThrows(IOException.class, () -> service.sendAsync(testRequest));

        assertFalse(service.getPendingRequests().containsKey(testRequest.getSocketMessageID()));
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

        Assert.assertThrows(IOException.class, () -> {
            service.sendSync(testRequest);
            assertFalse(service.getPendingRequests().containsKey(testRequest.getSocketMessageID()));
        });

    }

    @Test
    public void testCloseCommunication() {
        service.closeCommunication();

        assertTrue(service.getPendingRequests().isEmpty());
        assertNull(service.getSocket());
        assertThrows(IOException.class, () -> service.getDataOutputStream().writeUTF("abc"));
        assertThrows(EOFException.class , () -> service.getDataInputStream().readUTF());
    }

    @Test
    public void testRead() throws IOException {

        String testJson = SocketMessageFactory.createLoginRequest("abc", "def").toJson();

        dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeUTF(testJson);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        service.setStreamsForTest(new DataInputStream(byteIn), dataOutputStream);

        service.read();

        assertEquals(0, service.getDataInputStream().available());
    }
}
