package communication.dtos.responses.login;

import communication.user.UserDTO;
import communication.user.UserType;

public class LoginSuccessResponseDTO<U extends UserDTO> {

    private UserType userType;

    private U userDTO;

    public LoginSuccessResponseDTO(UserType userType, U userDTO) {
        this.userType = userType;
        this.userDTO = userDTO;
    }

    public U getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(U userDTO) {
        this.userDTO = userDTO;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
