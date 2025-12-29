package models.user.roles;

import communication.dtos.user.UserType;
import models.user.UserModel;

public class Role {

    private final UserModel user;
    private final UserType userType;

    protected Role(UserModel user, UserType userType) {
        this.user = user;
        this.userType = userType;
    }

    public UserModel getUser() {
        return user;
    }

    public UserType getUserType() {
        return userType;
    }
}
