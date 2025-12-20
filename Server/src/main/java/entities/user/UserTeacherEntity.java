package entities.user;

import communication.dtos.user.UserType;

public class UserTeacherEntity extends UserEntity {

    public UserTeacherEntity() {
        this.setUserType(UserType.TEACHER);
    }
}
