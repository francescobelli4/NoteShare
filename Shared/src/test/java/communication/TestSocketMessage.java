package communication;

import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.responses.login.LoginFailureResponseDTO;
import communication.dtos.responses.login.RegisterFailureReason;
import communication.dtos.responses.login.RegisterFailureResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TestSocketMessage {

    private SocketMessage socketMessage;
    private RegisterFailureResponseDTO registerFailureResponseDTO;

    @BeforeEach
    void setup() {
        registerFailureResponseDTO = new RegisterFailureResponseDTO(RegisterFailureReason.USERNAME_TOO_SHORT);
        socketMessage = new SocketMessage(SocketMessageType.REGISTER_FAILURE, "abc", registerFailureResponseDTO);
    }

    @Test
    void getSocketMessageType() {
        assertEquals(SocketMessageType.REGISTER_FAILURE, socketMessage.getSocketMessageType());
    }

    @Test
    void setSocketMessageType() {
        socketMessage.setSocketMessageType(SocketMessageType.LOGIN_FAILURE);
        assertEquals(SocketMessageType.LOGIN_FAILURE, socketMessage.getSocketMessageType());
    }

    @Test
    void getSocketMessageID() {
        assertEquals("abc", socketMessage.getSocketMessageID());
    }

    @Test
    void setSocketMessageID() {
        socketMessage.setSocketMessageID("def");
        assertEquals("def", socketMessage.getSocketMessageID());
    }

    @Test
    void getPayload() {
        assertEquals(registerFailureResponseDTO, socketMessage.getPayload());
    }

    @Test
    void setPayload() {
        LoginFailureResponseDTO f = new LoginFailureResponseDTO(LoginFailureReason.WRONG_USERNAME);
        socketMessage.setPayload(f);
        assertEquals(f, socketMessage.getPayload());
    }

    @Test
    void toJson() {
        String json = socketMessage.toJson();
        assertInstanceOf(RegisterFailureResponseDTO.class, SocketMessage.fromJson(json).getPayload());
    }
}