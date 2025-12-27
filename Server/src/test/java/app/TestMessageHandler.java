package app;

import communication.SocketMessage;
import communication.SocketMessageFactory;
import communication.SocketMessageType;
import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.responses.login.LoginFailureResponseDTO;
import communication.dtos.user.UserType;
import daos.message.MessageDAO;
import daos.message.NPMessageDAO;
import daos.user.NonPersistentUserDAO;
import daos.user.UserDAO;
import entities.user.UserStudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import utils.Hashing;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    void handleMessage_LoginUsingTokenFAIL() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginUsingTokenRequest("123");

        AppContext mockedCTX = mock(AppContext.class);
        UserDAO npUserDAO = new NonPersistentUserDAO();
        MessageDAO npMessageDAO = new NPMessageDAO();
        when(mockedCTX.getUserDAO()).thenReturn(npUserDAO);
        when(mockedCTX.getMessageDAO()).thenReturn(npMessageDAO);

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);

        // User with token not found //
        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);

        ArgumentCaptor<SocketMessage> msgCaptor = ArgumentCaptor.forClass(SocketMessage.class);
        verify(mockedNetworkUser, timeout(1000)).write(msgCaptor.capture());
        assertEquals(SocketMessageType.LOGIN_FAILURE, msgCaptor.getValue().getSocketMessageType());
    }

    @Test
    void handleMessage_LoginUsingTokenSUCCESS() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginUsingTokenRequest("123");

        AppContext mockedCTX = mock(AppContext.class);
        UserDAO npUserDAO = new NonPersistentUserDAO();
        MessageDAO npMessageDAO = mock(NPMessageDAO.class);
        when(npMessageDAO.get(anyString())).thenReturn(new ArrayList<>());
        when(mockedCTX.getUserDAO()).thenReturn(npUserDAO);
        when(mockedCTX.getMessageDAO()).thenReturn(npMessageDAO);

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);

        ArgumentCaptor<SocketMessage> msgCaptor = ArgumentCaptor.forClass(SocketMessage.class);

        // User with token found //
        UserStudentEntity u = new UserStudentEntity();
        u.setToken("123");
        u.setUserType(UserType.STUDENT);
        u.setUsername("TMO");
        u.setCoins(100);
        npUserDAO.saveUser(u);

        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);

        verify(mockedNetworkUser, timeout(2000).times(2)).write(msgCaptor.capture());

        assertEquals(SocketMessageType.ACCESS_SUCCESS, msgCaptor.getAllValues().get(0).getSocketMessageType());
        assertEquals(SocketMessageType.SET_MESSAGES, msgCaptor.getAllValues().get(1).getSocketMessageType());
    }

    @Test
    void handleMessage_LoginWRONGNAME() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginRequest("TMOOOO", "TMOOOO");

        AppContext mockedCTX = mock(AppContext.class);
        UserDAO npUserDAO = new NonPersistentUserDAO();
        MessageDAO npMessageDAO = mock(NPMessageDAO.class);
        when(npMessageDAO.get(anyString())).thenReturn(new ArrayList<>());
        when(mockedCTX.getUserDAO()).thenReturn(npUserDAO);
        when(mockedCTX.getMessageDAO()).thenReturn(npMessageDAO);

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);

        // User with token not found //
        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);

        ArgumentCaptor<SocketMessage> msgCaptor = ArgumentCaptor.forClass(SocketMessage.class);
        verify(mockedNetworkUser, timeout(1000)).write(msgCaptor.capture());
        assertEquals(SocketMessageType.LOGIN_FAILURE, msgCaptor.getValue().getSocketMessageType());
        assertEquals(LoginFailureReason.WRONG_USERNAME, ((LoginFailureResponseDTO)msgCaptor.getValue().getPayload()).getLoginFailureReason());
    }

    @Test
    void handleMessage_LoginWRONGPASSWORD() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginRequest("TMOOOO", "TMOOOO");

        AppContext mockedCTX = mock(AppContext.class);
        UserDAO npUserDAO = new NonPersistentUserDAO();
        MessageDAO npMessageDAO = mock(NPMessageDAO.class);
        when(npMessageDAO.get(anyString())).thenReturn(new ArrayList<>());
        when(mockedCTX.getUserDAO()).thenReturn(npUserDAO);
        when(mockedCTX.getMessageDAO()).thenReturn(npMessageDAO);

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);

        UserStudentEntity u = new UserStudentEntity();
        u.setToken("123");
        u.setUserType(UserType.STUDENT);
        u.setUsername("TMOOOO");
        u.setPassword(Hashing.hashString("ABC"));
        u.setCoins(100);
        npUserDAO.saveUser(u);

        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);
        ArgumentCaptor<SocketMessage> msgCaptor = ArgumentCaptor.forClass(SocketMessage.class);

        verify(mockedNetworkUser, timeout(1000)).write(msgCaptor.capture());
        assertEquals(SocketMessageType.LOGIN_FAILURE, msgCaptor.getValue().getSocketMessageType());
        assertEquals(LoginFailureReason.WRONG_PASSWORD, ((LoginFailureResponseDTO)msgCaptor.getValue().getPayload()).getLoginFailureReason());
    }

    @Test
    void handleMessage_LoginSUCCESS() throws NoSuchFieldException, IllegalAccessException {

        SocketMessage arrivingMessage = SocketMessageFactory.createLoginRequest("TMOOOO", "TMOOOO");

        AppContext mockedCTX = mock(AppContext.class);
        UserDAO npUserDAO = new NonPersistentUserDAO();
        MessageDAO npMessageDAO = mock(NPMessageDAO.class);
        when(npMessageDAO.get(anyString())).thenReturn(new ArrayList<>());
        when(mockedCTX.getUserDAO()).thenReturn(npUserDAO);
        when(mockedCTX.getMessageDAO()).thenReturn(npMessageDAO);

        // I should inject the mockedCTX in this case instead of using MockedStatic because
        // AppContext will be accessed by various threads, but MockedStatic's "thenReturn" only
        // works in the thread that is running the test!
        Field instanceField = AppContext.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockedCTX);

        ArgumentCaptor<SocketMessage> msgCaptor = ArgumentCaptor.forClass(SocketMessage.class);

        // User with token found //
        UserStudentEntity u = new UserStudentEntity();
        u.setToken("123");
        u.setUserType(UserType.STUDENT);
        u.setUsername("TMOOOO");
        u.setPassword(Hashing.hashString("TMOOOO"));
        u.setCoins(100);
        npUserDAO.saveUser(u);

        MessageHandler.getInstance().handleMessage(arrivingMessage.toJson(), mockedNetworkUser);

        verify(mockedNetworkUser, timeout(2000).times(2)).write(msgCaptor.capture());

        assertEquals(SocketMessageType.ACCESS_SUCCESS, msgCaptor.getAllValues().get(0).getSocketMessageType());
        assertEquals(SocketMessageType.SET_MESSAGES, msgCaptor.getAllValues().get(1).getSocketMessageType());
    }
}