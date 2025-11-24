package mappers;

import communication.user.UserAdminDTO;
import communication.user.UserDTO;
import communication.user.UserStudentDTO;
import communication.user.UserTeacherDTO;
import models.AdminUserModel;
import models.StudentUserModel;
import models.TeacherUserModel;
import models.UserModel;

public class UserMapper {

    private UserMapper() {}

    public static UserModel toModel(UserDTO userDTO) {

        return switch (userDTO.getUserType()) {
            case STUDENT -> toStudentUserModel((UserStudentDTO) userDTO);
            case TEACHER -> toTeacherUserModel((UserTeacherDTO) userDTO);
            case ADMINISTRATOR -> toAdminUserModel((UserAdminDTO) userDTO);
        };
    }

    private static StudentUserModel toStudentUserModel(UserStudentDTO studentDTO) {

        StudentUserModel studentUserModel = new StudentUserModel();
        studentUserModel.setUsername(studentDTO.getUsername());
        studentUserModel.setCoins(studentDTO.getCoins());

        return studentUserModel;
    }

    private static TeacherUserModel toTeacherUserModel(UserTeacherDTO teacherDTO) {
        return new TeacherUserModel();
    }

    private static AdminUserModel toAdminUserModel(UserAdminDTO adminDTO) {
        return new AdminUserModel();
    }
}
