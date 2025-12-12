package communication.dtos.responses.login;

public enum RegisterFailureReason {
    USERNAME_ALREADY_TAKEN,
    USERNAME_TOO_SHORT,
    USERNAME_TOO_LONG,
    PASSWORD_TOO_LONG,
    PASSWORD_TOO_SHORT
}
