package app.mvc.mappers;

import app.mvc.models.UserModel;
import persistency.dtos.UserDTO;

/**
 * This class should only map UserModel <---> UserDTO
 */
public class UserMapper {

    public static UserDTO toDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userModel.getUsername());
        userDTO.setUserType(userModel.getUserType());
        userDTO.setCoins(userModel.getCoins());
        return userDTO;
    };

    public static void mapToModel(UserDTO userDTO) {
        UserModel userModel = UserModel.getInstance();
        userModel.setUsername(userDTO.getUsername());
        userModel.setUserType(userDTO.getUserType());
        userModel.setCoins(userDTO.getCoins());
    }

    public static void mapToModel(UserDTO userDTO, String token) {
        UserModel userModel = UserModel.getInstance();
        userModel.setUsername(userDTO.getUsername());
        userModel.setUserType(userDTO.getUserType());
        userModel.setCoins(userDTO.getCoins());
        userModel.setToken(token);
    }
}
