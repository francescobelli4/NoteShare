package app;

import daos.message.NPMessageDAO;
import daos.user.NonPersistentUserDAO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.mockito.Mockito.*;

class TestServer {

    @Test
    void initializeServer() {

        String[] args = {"demo"};

        AppContext mockedCtx = mock(AppContext.class);
        when(mockedCtx.getUserDAO()).thenReturn(new NonPersistentUserDAO());
        when(mockedCtx.getMessageDAO()).thenReturn(new NPMessageDAO());

        CommunicationLayer mockedCommunicationLayer = mock(CommunicationLayer.class);

        try (
                MockedStatic<AppContext> staticCtx = mockStatic(AppContext.class);
                MockedStatic<CommunicationLayer> staticCommunicationLayer = mockStatic(CommunicationLayer.class)
        ) {

            staticCtx.when(AppContext::getInstance).thenReturn(mockedCtx);
            staticCommunicationLayer.when(CommunicationLayer::getInstance).thenReturn(mockedCommunicationLayer);

            Server.initializeServer(args);

            verify(mockedCtx).setOptions(any());
            verify(mockedCommunicationLayer).initializeServerSocket();
        } catch (IOException _) {
            //...
        }
    }
}