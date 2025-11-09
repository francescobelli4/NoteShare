package communication.requests;

import communication.Transferable;

/**
 * This message is sent from client to server to log in.
 */
public class LoginRequest extends Transferable {

    private final String username;
    private final String password;


    public LoginRequest(String username, String password) {

        id = 2;

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
