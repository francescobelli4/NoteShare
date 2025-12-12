package communication.dtos.responses.login;

public class RegisterFailureResponseDTO {

    private RegisterFailureReason registerFailureReason;

    public RegisterFailureResponseDTO(RegisterFailureReason registerFailureReason) {
        this.registerFailureReason = registerFailureReason;
    }

    public RegisterFailureReason getRegisterFailureReason() {
        return registerFailureReason;
    }

    public void setRegisterFailureReason(RegisterFailureReason registerFailureReason) {
        this.registerFailureReason = registerFailureReason;
    }
}
