package communication.dtos.requests.register;

import communication.user.UserDTO;

public class RegisterRequestDTO<U extends UserDTO> {

    private U userDTO;
    private String password;

    public RegisterRequestDTO(U userDTO, String password) {
        this.userDTO = userDTO;
        this.password = password;
    }

    public void setUserDTO(U userDTO) {
        this.userDTO = userDTO;
    }

    public U getUserDTO() {
        return userDTO;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
