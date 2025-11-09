package communication.requests;

import communication.Transferable;

/**
 * This message is sent from client to server to log in.
 */
public class LoginRequest extends Transferable {

    public String username;
    public String password;


    public LoginRequest(String username, String password) {

        id = 2;

        this.username = username;
        this.password = password;
    }
}
