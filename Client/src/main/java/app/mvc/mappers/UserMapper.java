package app.mvc.mappers;

import app.mvc.models.UserModel;
import persistency.dtos.UserDTO;

/**
 * This class should only map UserModel <---> UserDTO
 */
public class UserMapper {

    /**
     * This function builds a UserDTO from a UserModel
     * @param userModel the current user app status
     * @return the data transfer object
     */
    public static UserDTO toDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userModel.getUsername());
        userDTO.setUserType(userModel.getUserType());
        userDTO.setCoins(userModel.getCoins());
        return userDTO;
    };

    /**
     * This function populates the UserModel starting from a UserDTO.
     * @param userDTO the dto received from the server
     * @param token the access token
     */
    public static void mapToModel(UserDTO userDTO, String token) {
        UserModel userModel = UserModel.getInstance();
        userModel.setUsername(userDTO.getUsername());
        userModel.setUserType(userDTO.getUserType());
        userModel.setCoins(userDTO.getCoins());
        userModel.setToken(token);
    }
}
