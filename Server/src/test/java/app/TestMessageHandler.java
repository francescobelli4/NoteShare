package app;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.dtos.responses.login.LoginFailureReason;
import daos.user.NonPersistentUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestMessageHandler {

    private NetworkUser mockedNetworkUser;

    @BeforeEach
    void setup() {
        mockedNetworkUser = mock(NetworkUser.class);
    }

    @Test
    void getInstance() {

        MessageHandler instance = MessageHandler.getInstance();
        assertNotNull(instance);

        MessageHandler.reset();

        MessageHandler newInstance = MessageHandler.getInstance();
        assertNotNull(newInstance);
        assertNotEquals(instance, newInstance);

        MessageHandler newInstance2 = MessageHandler.getInstance();
        assertEquals(newInstance, newInstance2);
    }

    @Test
    void handleMessage_LoginUsingToken() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginUsingTokenRequest("123");

        // User with token not found //

        AppContext mockedCTX = mock(AppContext.class);
        when(mockedCTX.getUserDAO()).thenReturn(new NonPersistentUserDAO());

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);
        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);

        verify(mockedNetworkUser, timeout(2000)).write(SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_TOKEN, any()));
    }
}