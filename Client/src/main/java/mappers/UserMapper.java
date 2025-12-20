package mappers;

import communication.dtos.user.UserAdminDTO;
import communication.dtos.user.UserDTO;
import communication.dtos.user.UserStudentDTO;
import communication.dtos.user.UserTeacherDTO;
import models.user.AdminUserModel;
import models.user.StudentUserModel;
import models.user.TeacherUserModel;
import models.user.UserModel;

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
     * @param userModel the model that has to be populated
     * @param userDTO the UserDTO that has to be converted
     */
    public static UserModel populateModel(UserModel userModel, UserDTO userDTO) {

        UserModel newModel = toModel(userDTO);

        for (UserModel.LoginListener l : userModel.getLoginListeners()) {
            newModel.addUserLoginListener(l);
        }

        return newModel;
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
