package communication.requests;

import communication.Transferable;

public class TokenLoginRequest extends Transferable {

    public String token;

    public TokenLoginRequest(String token) {

        id = 4;

        this.token = token;
    }
}
