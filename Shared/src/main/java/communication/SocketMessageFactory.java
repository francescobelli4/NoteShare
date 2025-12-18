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

    public static SocketMessage createLoginFailureResponse(LoginFailureReason loginFailureReason, String messageID) {
        return new SocketMessage(SocketMessageType.LOGIN_FAILURE, messageID, new LoginFailureResponseDTO(loginFailureReason));
    }

    public static SocketMessage createRegisterRequest(String username, String password, UserType userType) {
        return new SocketMessage(SocketMessageType.REGISTER_REQUEST, new RegisterRequestDTO(username, password, userType));
    }

    public static SocketMessage createRegisterFailureResponse(RegisterFailureReason registerFailureReason, String messageID) {
        return new SocketMessage(SocketMessageType.REGISTER_FAILURE, messageID, new RegisterFailureResponseDTO(registerFailureReason));
    }

    public static SocketMessage createAccessSuccessResponse(UserDTO userDTO, String messageID, String accessToken) {
        return new SocketMessage(SocketMessageType.ACCESS_SUCCESS, messageID, new AccessSuccessResponseDTO<>(userDTO.getUserType(), userDTO, accessToken));
    }
}
