package communication.dtos.responses.login;

public enum LoginFailureReason {
    WRONG_USERNAME,
    WRONG_PASSWORD,
    USERNAME_TOO_SHORT,
    USERNAME_TOO_LONG,
    PASSWORD_TOO_LONG,
    PASSWORD_TOO_SHORT,
    WRONG_TOKEN
}
