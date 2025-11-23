package communication;

import communication.dtos.LoginRequestDTO;
import communication.dtos.LoginUsingTokenRequestDTO;
import communication.dtos.RegisterRequestDTO;

public class SocketMessageFactory {

    public static SocketMessage createLoginRequest(String username, String password) {
        return new SocketMessage(SocketMessageType.LOGIN_REQUEST, new LoginRequestDTO(username, password));
    }

    public static SocketMessage createLoginUsingTokenRequest(String token) {
        return new SocketMessage(SocketMessageType.LOGIN_USING_TOKEN_REQUEST, new LoginUsingTokenRequestDTO(token));
    }

    public static SocketMessage createRegisterRequest(String username, String password, String userType) {
        return new SocketMessage(SocketMessageType.REGISTER_REQUEST, new RegisterRequestDTO(username, password, userType));
    }
}
