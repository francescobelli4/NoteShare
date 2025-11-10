package persistency.shared.mappers;

import persistency.dtos.UserDTO;
import persistency.shared.entities.UserEntity;

/**
 * This class should only map UserEntity <---> UserDTO
 */
public class UserMapper {

    private UserMapper() {}

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setUserType(userEntity.getUserType());
        userDTO.setCoins(userEntity.getCoins());
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserType(userDTO.getUserType());
        userEntity.setCoins(userDTO.getCoins());
        return userEntity;
    }
}
