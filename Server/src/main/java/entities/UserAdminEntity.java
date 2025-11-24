package entities;

import communication.user.UserType;

public class UserAdminEntity extends UserEntity {

    public UserAdminEntity() {
        this.setUserType(UserType.ADMINISTRATOR);
    }
}
