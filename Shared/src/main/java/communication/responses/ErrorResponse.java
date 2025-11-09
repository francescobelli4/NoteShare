package communication.responses;

import communication.Transferable;

/**
 * ERROR CODES
 * 0: Register username already taken
 * 1: Login failed (user not found)
 * 2: Login failed (wrong password)
 */
public class ErrorResponse extends Transferable {

    public int error_code;

    public ErrorResponse(int error_code) {

        id = 3;

        this.error_code = error_code;
    }
}
