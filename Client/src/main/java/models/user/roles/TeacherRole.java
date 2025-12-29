package models.user.roles;

import communication.dtos.user.UserType;
import models.user.UserModel;

public class TeacherRole extends Role {

    public TeacherRole(UserModel user) {
        super(user, UserType.TEACHER);
    }
}
