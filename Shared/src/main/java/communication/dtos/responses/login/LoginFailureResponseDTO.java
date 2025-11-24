package communication.dtos.responses.login;

public class LoginFailureResponseDTO {

    private LoginFailureReason loginFailureReason;

    public LoginFailureResponseDTO(LoginFailureReason loginFailureReason) {
        this.loginFailureReason = loginFailureReason;
    }

    public LoginFailureReason getLoginFailureReason() {
        return loginFailureReason;
    }

    public void setLoginFailureReason(LoginFailureReason loginFailureReason) {
        this.loginFailureReason = loginFailureReason;
    }
}
