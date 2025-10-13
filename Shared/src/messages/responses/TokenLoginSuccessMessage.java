package messages.responses;

import dtos.UserDTO;
import messages.Message;

/**
 * This message is sent from server to client to notify the user that the
 * registration was successful.
 *
 * It sends a UserDTO so the client can set up the user profile and an access token
 * to allow the automatic login
 */
public class TokenLoginSuccessMessage extends Message {

    public UserDTO userDTO;
    public String token;

    public TokenLoginSuccessMessage(UserDTO userDTO, String token) {

        id = 102;
        this.userDTO = userDTO;
        this.token = token;
    }
}
