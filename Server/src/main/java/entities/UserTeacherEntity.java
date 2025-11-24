package entities;

import communication.user.UserType;

public class UserTeacherEntity extends UserEntity {

    public UserTeacherEntity() {
        this.setUserType(UserType.TEACHER);
    }
}
