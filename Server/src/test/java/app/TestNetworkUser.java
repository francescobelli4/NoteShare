package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TestNetworkUser {

    private Thread serverThread;
    private ServerSocket server;
    private Socket socket;
    private NetworkUser user;

    @BeforeEach
    void setup() throws IOException {

        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
            serverThread = null;
        }

        server = new ServerSocket(0);

        serverThread = new Thread(() -> {
            try {
                Socket c = server.accept();

                DataOutputStream o = new DataOutputStream(c.getOutputStream());

                o.writeUTF(":D");

                c.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        socket = new Socket(server.getInetAddress(), server.getLocalPort());
        user = new NetworkUser(socket);
    }

    @Test
    void run() {
        MessageHandler mockHandler = mock(MessageHandler.class);

        try (MockedStatic<MessageHandler> staticMock = mockStatic(MessageHandler.class)) {
            staticMock.when(MessageHandler::getInstance).thenReturn(mockHandler);

            user.run();

            verify(mockHandler, times(1)).handleMessage(anyString(), any(NetworkUser.class));

            assertTrue(socket.isClosed());
            assertNull(user.getDataInputStream());
            assertNull(user.getDataOutputStream());
        }
    }
}