package exceptions;

import communication.dtos.responses.login.LoginFailureReason;

public class LoginFailureException extends RuntimeException {
    private final LoginFailureReason loginFailureReason;

    public LoginFailureException(LoginFailureReason loginFailureReason) {
        super("Login failed");
        this.loginFailureReason = loginFailureReason;
    }

    public LoginFailureReason getLoginFailureReason() {
        return loginFailureReason;
    }
}
