package communication;

import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.LoginFailureReason;
import communication.dtos.responses.login.LoginFailureResponseDTO;
import communication.dtos.responses.login.LoginSuccessResponseDTO;
import communication.user.UserDTO;

public class SocketMessageFactory {

    private SocketMessageFactory() {}

    public static SocketMessage createLoginRequest(String username, String password) {
        return new SocketMessage(SocketMessageType.LOGIN_REQUEST, new LoginRequestDTO(username, password));
    }

    public static SocketMessage createLoginUsingTokenRequest(String token) {
        return new SocketMessage(SocketMessageType.LOGIN_USING_TOKEN_REQUEST, new LoginUsingTokenRequestDTO(token));
    }

    public static SocketMessage createRegisterRequest(String username, String password, String userType) {
        return new SocketMessage(SocketMessageType.REGISTER_REQUEST, new RegisterRequestDTO(username, password, userType));
    }

    public static SocketMessage createLoginSuccessResponse(UserDTO userDTO, String messageID) {
        return new SocketMessage(SocketMessageType.LOGIN_SUCCESS, messageID, new LoginSuccessResponseDTO(userDTO.getUserType(), userDTO));
    }

    public static SocketMessage createLoginFailureResponse(LoginFailureReason loginFailureReason, String messageID) {
        return new SocketMessage(SocketMessageType.LOGIN_FAILURE, messageID, new LoginFailureResponseDTO(loginFailureReason));
    }
}
