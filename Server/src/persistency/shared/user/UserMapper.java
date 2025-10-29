package persistency.shared.user;

import persistency.shared.dtos.UserDTO;

/**
 * This class should only map UserEntity <---> UserDTO
 */
public class UserMapper {

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setUserType(userEntity.getUserType());
        userDTO.setCoins(userEntity.getCoins());
        return userDTO;
    };

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserType(userDTO.getUserType());
        userEntity.setCoins(userDTO.getCoins());
        return userEntity;
    }
}
