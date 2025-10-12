package messages.responses;

import dtos.UserDTO;
import messages.Message;

/**
 * This message is sent from server to client to notify the user that the
 * registration was successful.
 *
 * It sends a UserDTO so the client can set up the user profile
 */
public class RegisterSuccessMessage extends Message {

    public UserDTO userDTO;

    public RegisterSuccessMessage(UserDTO userDTO) {

        id = 100;
        this.userDTO = userDTO;
    }
}
