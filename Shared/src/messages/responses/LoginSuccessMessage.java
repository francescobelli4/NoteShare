package messages.responses;

import persistency.dtos.UserDTO;
import messages.Message;

/**
 * This message is sent from server to client to notify the user that the
 * login was successful.
 *
 * It sends a UserDTO so the client can set up the user profile and an access token to
 * allow the automatic login
 */
public class LoginSuccessMessage extends Message {

    public UserDTO userDTO;
    public String token;

    public LoginSuccessMessage(UserDTO userDTO, String token) {

        id = 101;
        this.userDTO = userDTO;
        this.token = token;
    }
}
