package communication.dtos.responses.login;

import communication.user.UserDTO;
import communication.user.UserType;

public class RegisterSuccessResponseDTO {

    private UserType userType;

    private UserDTO userDTO;

    private String accessToken;

    public RegisterSuccessResponseDTO(UserType userType, UserDTO userDTO, String accessToken) {
        this.userType = userType;
        this.userDTO = userDTO;
        this.accessToken = accessToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
