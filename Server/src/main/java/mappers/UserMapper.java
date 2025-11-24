package mappers;

import communication.user.UserAdminDTO;
import communication.user.UserDTO;
import communication.user.UserStudentDTO;
import communication.user.UserTeacherDTO;
import entities.UserAdminEntity;
import entities.UserEntity;
import entities.UserStudentEntity;
import entities.UserTeacherEntity;

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

        return userStudentDTO;
    }

    private static UserTeacherDTO toUserTeacherDTO(UserTeacherEntity teacherEntity) {
        return new UserTeacherDTO();
    }

    private static UserAdminDTO toUserAdminDTO(UserAdminEntity adminEntity) {
        return new UserAdminDTO();
    }
}
