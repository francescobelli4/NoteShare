package models.user;

import communication.dtos.user.UserType;

public class TeacherUserModel extends UserModel {

    public TeacherUserModel() {
        setUserType(UserType.TEACHER);
    }
}
