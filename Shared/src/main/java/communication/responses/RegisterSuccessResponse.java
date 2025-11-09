package communication.responses;

import communication.Transferable;
import persistency.dtos.UserDTO;

/**
 * This message is sent from server to client to notify the user that the
 * registration was successful.
 *
 * It sends a UserDTO so the client can set up the user profile
 */
public class RegisterSuccessResponse extends Transferable {

    private final UserDTO userDTO;
    private final String token;

    public RegisterSuccessResponse(UserDTO userDTO, String token) {

        id = 100;
        this.userDTO = userDTO;
        this.token = token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String getToken() {
        return token;
    }
}
