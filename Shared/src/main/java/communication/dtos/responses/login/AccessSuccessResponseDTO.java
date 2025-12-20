package communication.dtos.responses.login;

import communication.dtos.user.UserDTO;
import communication.dtos.user.UserType;

public class AccessSuccessResponseDTO<U extends UserDTO> {

    private UserType userType;

    private U userDTO;

    private String accessToken;

    public AccessSuccessResponseDTO(UserType userType, U userDTO, String accessToken) {
        this.userType = userType;
        this.userDTO = userDTO;
        this.accessToken = accessToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
