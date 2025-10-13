package messages.responses;

import messages.Message;

/**
 * ERROR CODES
 * 0: Register username already taken
 * 1: Login failed (user not found)
 * 2: Login failed (wrong password)
 */
public class ErrorMessage extends Message {

    public int error_code;

    public ErrorMessage(int error_code) {

        id = 3;

        this.error_code = error_code;
    }
}
