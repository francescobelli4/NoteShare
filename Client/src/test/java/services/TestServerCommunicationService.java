package services;

import com.google.gson.Gson;
import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import exceptions.UnrecognisedResponseException;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.*;
import static org.junit.Assert.assertTrue;

public class TestServerCommunicationService {

    private ServerCommunicationService service;
    private DataOutputStream dataOutputStream;


    @Before
    public void setUp() {
        // Getting an instance of ServerCommunicationService
        service = ServerCommunicationService.getInstance();

        // Setting a mocked outputStream in service
        dataOutputStream = new DataOutputStream(OutputStream.nullOutputStream());
        service.setStreamsForTest(null, dataOutputStream);
    }

    @Test
    public void sendAsync_ShouldRegisterFutureAndWriteCorrectJson() throws Exception {

        // The fake message that will be sent through the dataOutputStream
        SocketMessage testRequest = new SocketMessage(SocketMessageType.LOGIN_REQUEST, "test_payload");
        CompletableFuture<SocketMessage> future = service.sendAsync(testRequest);

        assertTrue(service.getPendingRequests().containsKey(testRequest.getSocketMessageID()));
    }
}
