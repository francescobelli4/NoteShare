package communication.responses;

import communication.Transferable;

/**
 * ERROR CODES
 * 0: Register username already taken
 * 1: Login failed (user not found)
 * 2: Login failed (wrong password)
 */
public class ErrorResponse extends Transferable {

    private final int errorCode;

    public ErrorResponse(int errorCode) {

        id = 3;

        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
