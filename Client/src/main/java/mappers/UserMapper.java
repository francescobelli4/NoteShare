package mappers;

import communication.dtos.user.*;
import models.user.UserModel;
import models.user.roles.AdminRole;
import models.user.roles.Role;
import models.user.roles.StudentRole;
import models.user.roles.TeacherRole;

public class UserMapper {

    private UserMapper() {}

    /**
     * This function should convert a UserDTO to a UserModel subclass.
     * @param userDTO the UserDTO that has to be converted
     * @return a UserModel subclass
     */
    public static UserModel toModel(UserDTO userDTO) {

        return switch (userDTO.getUserType()) {
            case STUDENT -> toStudentUserModel((UserStudentDTO) userDTO);
            case TEACHER -> toTeacherUserModel((UserTeacherDTO) userDTO);
            case ADMINISTRATOR -> toAdminUserModel((UserAdminDTO) userDTO);
        };
    }

    /**
     * This function should populate a UserModel using a UserDTO
     * @param user the model that has to be populated
     * @param userDTO the UserDTO that has to be converted
     */
    public static void populateModel(UserModel user, UserDTO userDTO) {

        Role role = switch (userDTO.getUserType()) {
            case STUDENT -> new StudentRole(user);
            case TEACHER -> new TeacherRole(user);
            case ADMINISTRATOR -> new AdminRole(user);
        };

        user.setUsername(userDTO.getUsername());
        user.setRole(role);

        user.as(StudentRole.class).ifPresent(studentRole -> studentRole.setCoins(((UserStudentDTO)userDTO).getCoins()));
    }

    private static UserModel toStudentUserModel(UserStudentDTO userStudentDTO) {
        UserModel user = new UserModel(userStudentDTO.getUsername(), UserType.STUDENT);

        user.as(StudentRole.class).ifPresent(studentRole -> studentRole.setCoins(userStudentDTO.getCoins()));
        return user;
    }

    private static UserModel toTeacherUserModel(UserTeacherDTO userTeacherDTO) {
        return new UserModel(userTeacherDTO.getUsername(), UserType.TEACHER);
    }

    private static UserModel toAdminUserModel(UserAdminDTO userAdminDTO) {
        return new UserModel(userAdminDTO.getUsername(), UserType.ADMINISTRATOR);
    }
}
