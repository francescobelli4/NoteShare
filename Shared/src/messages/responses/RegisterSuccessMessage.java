package messages.responses;

import memory.shared.dtos.UserDTO;
import messages.Message;

/**
 * This message is sent from server to client to notify the user that the
 * registration was successful.
 *
 * It sends a UserDTO so the client can set up the user profile
 */
public class RegisterSuccessMessage extends Message {

    public UserDTO userDTO;
    public String token;

    public RegisterSuccessMessage(UserDTO userDTO, String token) {

        id = 100;
        this.userDTO = userDTO;
        this.token = token;
    }
}
