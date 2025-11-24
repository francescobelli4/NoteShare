package models;

import communication.user.UserType;

public class TeacherUserModel extends UserModel {

    public TeacherUserModel() {
        setUserType(UserType.TEACHER);
    }
}
