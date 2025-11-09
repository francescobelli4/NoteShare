package communication.requests;

import communication.Transferable;

public class TokenLoginRequest extends Transferable {

    private final String token;

    public TokenLoginRequest(String token) {

        id = 4;

        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
