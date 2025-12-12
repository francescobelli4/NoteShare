package exceptions;

import communication.dtos.responses.login.RegisterFailureReason;

public class RegisterFailureException extends RuntimeException {
    private final RegisterFailureReason registerFailureReason;

    public RegisterFailureException(RegisterFailureReason registerFailureReason) {
        super("Register failed");
        this.registerFailureReason = registerFailureReason;
    }

    public RegisterFailureReason getRegisterFailureReason() {
        return registerFailureReason;
    }
}
