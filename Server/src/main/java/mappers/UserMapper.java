package mappers;

import communication.dtos.user.UserAdminDTO;
import communication.dtos.user.UserDTO;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserTeacherDTO;
import entities.user.UserAdminEntity;
import entities.user.UserEntity;
import entities.user.UserStudentEntity;
import entities.user.UserTeacherEntity;
import utils.Utils;

public class UserMapper {

    private UserMapper() {}

    /**
     * This function should map a UserEntity to a UserDTO.
     * The specific User(userType)DTO can be obtained by casting this UserDTO
     * basing on UserDTO.userType
     * @param userEntity the generic student entity
     * @return a generic UserDTO that can be cast to a specific User(userType)DTO basing
     *         on the userType
     */
    public static UserDTO toDTO(UserEntity userEntity) {
        return switch (userEntity.getUserType()) {
            case STUDENT -> toUserStudentDTO((UserStudentEntity) userEntity);
            case TEACHER -> toUserTeacherDTO((UserTeacherEntity) userEntity);
            case ADMINISTRATOR -> toUserAdminDTO((UserAdminEntity) userEntity);
        };
    }

    private static UserStudentDTO toUserStudentDTO(UserStudentEntity studentEntity) {

        UserStudentDTO userStudentDTO = new UserStudentDTO();
        userStudentDTO.setUsername(studentEntity.getUsername());
        userStudentDTO.setCoins(studentEntity.getCoins());
        userStudentDTO.setUserType(studentEntity.getUserType());

        return userStudentDTO;
    }

    private static UserTeacherDTO toUserTeacherDTO(UserTeacherEntity teacherEntity) {
        return new UserTeacherDTO();
    }

    private static UserAdminDTO toUserAdminDTO(UserAdminEntity adminEntity) {
        return new UserAdminDTO();
    }

    public static UserEntity toEntity(UserDTO userDTO, String password) {
        return switch (userDTO.getUserType()) {
            case STUDENT -> toUserStudentEntity(userDTO, password);
            case TEACHER -> toUserTeacherEntity(userDTO, password);
            case ADMINISTRATOR -> toUserAdminEntity(userDTO, password);
        };
    }

    private static UserStudentEntity toUserStudentEntity(UserDTO userDTO, String password) {
        UserStudentEntity userStudentEntity = new UserStudentEntity();
        userStudentEntity.setCoins(((UserStudentDTO)userDTO).getCoins());
        userStudentEntity.setUsername(userDTO.getUsername());
        userStudentEntity.setPassword(password);
        userStudentEntity.setUserType(userDTO.getUserType());
        userStudentEntity.setToken(Utils.generateAccessToken());

        return userStudentEntity;
    }

    private static UserTeacherEntity toUserTeacherEntity(UserDTO userDTO, String password) {
        return new UserTeacherEntity();
    }

    private static UserAdminEntity toUserAdminEntity(UserDTO userDTO, String password) {
        return new UserAdminEntity();
    }
}
