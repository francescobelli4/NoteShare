package entities.user;

import communication.dtos.user.UserType;

public class UserAdminEntity extends UserEntity {

    public UserAdminEntity() {
        this.setUserType(UserType.ADMINISTRATOR);
    }
}
