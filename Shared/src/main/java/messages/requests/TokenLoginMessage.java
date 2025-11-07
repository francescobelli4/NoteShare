package messages.requests;

import messages.Message;

public class TokenLoginMessage extends Message {

    public String token;

    public TokenLoginMessage(String token) {

        id = 4;

        this.token = token;
    }
}
