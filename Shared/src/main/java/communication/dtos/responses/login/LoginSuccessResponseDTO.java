package communication.dtos.responses.login;

import communication.user.UserDTO;
import communication.user.UserType;

public class LoginSuccessResponseDTO {

    private UserType userType;

    private UserDTO userDTO;

    public LoginSuccessResponseDTO(UserType userType, UserDTO userDTO) {
        this.userType = userType;
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
