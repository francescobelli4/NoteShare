package communication;

import communication.dtos.requests.login.LoginRequestDTO;
import communication.dtos.requests.login.LoginUsingTokenRequestDTO;
import communication.dtos.requests.register.RegisterRequestDTO;
import communication.dtos.responses.login.*;
import communication.user.UserDTO;
import communication.user.UserType;

public class SocketMessageFactory {

    private SocketMessageFactory() {}

    public static SocketMessage createLoginRequest(String username, String password) {
        return new SocketMessage(SocketMessageType.LOGIN_REQUEST, new LoginRequestDTO(username, password));
    }

    public static SocketMessage createLoginUsingTokenRequest(String token) {
        return new SocketMessage(SocketMessageType.LOGIN_USING_TOKEN_REQUEST, new LoginUsingTokenRequestDTO(token));
    }

    public static SocketMessage createLoginSuccessResponse(UserDTO userDTO, String messageID, String accessToken) {
        return new SocketMessage(SocketMessageType.LOGIN_SUCCESS, messageID, new LoginSuccessResponseDTO<>(userDTO.getUserType(), userDTO, accessToken));
    }

    public static SocketMessage createLoginFailureResponse(LoginFailureReason loginFailureReason, String messageID) {
        return new SocketMessage(SocketMessageType.LOGIN_FAILURE, messageID, new LoginFailureResponseDTO(loginFailureReason));
    }

    public static SocketMessage createRegisterRequest(String username, String password, UserType userType) {
        return new SocketMessage(SocketMessageType.REGISTER_REQUEST, new RegisterRequestDTO(username, password, userType));
    }

    public static SocketMessage createRegisterSuccessRequest(UserDTO userDTO, String accessToken, String messageID) {
        return new SocketMessage(SocketMessageType.REGISTER_SUCCESS, messageID, new RegisterSuccessResponseDTO(userDTO.getUserType(), userDTO, accessToken));
    }

    public static SocketMessage createRegisterFailureResponse(RegisterFailureReason registerFailureReason, String messageID) {
        return new SocketMessage(SocketMessageType.REGISTER_FAILURE, messageID, new RegisterFailureResponseDTO(registerFailureReason));
    }
}
