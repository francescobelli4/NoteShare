package communication;

import communication.dtos.message.MessageDTO;
import communication.dtos.notification.message.MessageNotificationDTO;
import communication.dtos.notification.message.MessagesNotificationDTO;
import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.*;
import communication.dtos.user.UserDTO;
import communication.dtos.user.UserType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SocketMessageFactoryTest {

    @Test
    void createLoginRequest() {
        SocketMessage msg = SocketMessageFactory.createLoginRequest("Username", "Password");

        assertNotNull(msg);
        assertEquals(SocketMessageType.LOGIN_REQUEST, msg.getSocketMessageType());

        assertInstanceOf(LoginRequestDTO.class, msg.getPayload());

        LoginRequestDTO payload = (LoginRequestDTO) msg.getPayload();
        assertEquals("Username", payload.getUsername());
        assertEquals("Password", payload.getPassword());
    }

    @Test
    void createLoginUsingTokenRequest() {
        SocketMessage msg = SocketMessageFactory.createLoginUsingTokenRequest("Token");

        assertNotNull(msg);
        assertEquals(SocketMessageType.LOGIN_USING_TOKEN_REQUEST, msg.getSocketMessageType());

        assertInstanceOf(LoginUsingTokenRequestDTO.class, msg.getPayload());

        LoginUsingTokenRequestDTO payload = (LoginUsingTokenRequestDTO) msg.getPayload();
        assertEquals("Token", payload.getToken());
    }

    @Test
    void createLoginFailureResponse() {
        SocketMessage msg = SocketMessageFactory.createLoginFailureResponse(LoginFailureReason.WRONG_PASSWORD, "123");

        assertNotNull(msg);
        assertEquals(SocketMessageType.LOGIN_FAILURE, msg.getSocketMessageType());
        assertEquals("123", msg.getSocketMessageID());

        assertInstanceOf(LoginFailureResponseDTO.class, msg.getPayload());

        LoginFailureResponseDTO payload = (LoginFailureResponseDTO) msg.getPayload();
        assertEquals(LoginFailureReason.WRONG_PASSWORD, payload.getLoginFailureReason());
    }

    @Test
    void createRegisterRequest() {
        SocketMessage msg = SocketMessageFactory.createRegisterRequest("Username", "Password", UserType.STUDENT);

        assertNotNull(msg);
        assertEquals(SocketMessageType.REGISTER_REQUEST, msg.getSocketMessageType());

        assertInstanceOf(RegisterRequestDTO.class, msg.getPayload());

        RegisterRequestDTO payload = (RegisterRequestDTO) msg.getPayload();
        assertEquals("Username", payload.getUsername());
        assertEquals("Password", payload.getPassword());
        assertEquals(UserType.STUDENT, payload.getUserType());
    }

    @Test
    void createRegisterFailureResponse() {
        SocketMessage msg = SocketMessageFactory.createRegisterFailureResponse(RegisterFailureReason.USERNAME_ALREADY_TAKEN, "123");

        assertNotNull(msg);
        assertEquals(SocketMessageType.REGISTER_FAILURE, msg.getSocketMessageType());
        assertEquals("123", msg.getSocketMessageID());

        assertInstanceOf(RegisterFailureResponseDTO.class, msg.getPayload());

        RegisterFailureResponseDTO payload = (RegisterFailureResponseDTO) msg.getPayload();
        assertEquals(RegisterFailureReason.USERNAME_ALREADY_TAKEN, payload.getRegisterFailureReason());
    }

    @Test
    void createAccessSuccessResponse() {

        UserDTO mockUser = mock(UserDTO.class);
        when(mockUser.getUserType()).thenReturn(UserType.STUDENT);

        SocketMessage msg = SocketMessageFactory.createAccessSuccessResponse(mockUser, "123", "abc");

        assertNotNull(msg);
        assertEquals(SocketMessageType.ACCESS_SUCCESS, msg.getSocketMessageType());
        assertEquals("123", msg.getSocketMessageID());

        assertInstanceOf(AccessSuccessResponseDTO.class, msg.getPayload());

        AccessSuccessResponseDTO<?> payload = (AccessSuccessResponseDTO<?>) msg.getPayload();
        assertEquals("abc", payload.getAccessToken());
        assertEquals(mockUser, payload.getUserDTO());
        assertEquals(UserType.STUDENT, payload.getUserType());
    }

    @Test
    void createMessagesSetNotification() {
        List<MessageDTO> mockList = List.of(mock(MessageDTO.class), mock(MessageDTO.class));

        SocketMessage msg = SocketMessageFactory.createMessagesSetNotification(mockList);

        assertNotNull(msg);
        assertEquals(SocketMessageType.SET_MESSAGES, msg.getSocketMessageType());

        assertInstanceOf(MessagesNotificationDTO.class, msg.getPayload());

        MessagesNotificationDTO payload = (MessagesNotificationDTO) msg.getPayload();
        assertEquals(2, payload.getMessages().size());
        assertEquals(mockList, payload.getMessages());
    }

    @Test
    void createMessageAddNotification() {
        MessageDTO mockMessage = mock(MessageDTO.class);

        SocketMessage msg = SocketMessageFactory.createMessageAddNotification(mockMessage);

        assertNotNull(msg);
        assertEquals(SocketMessageType.ADD_MESSAGE, msg.getSocketMessageType());

        assertInstanceOf(MessageNotificationDTO.class, msg.getPayload());

        MessageNotificationDTO payload = (MessageNotificationDTO) msg.getPayload();
        assertEquals(mockMessage, payload.getMessage());
    }
}