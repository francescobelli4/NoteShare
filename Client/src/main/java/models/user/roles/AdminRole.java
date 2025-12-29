package models.user.roles;

import communication.dtos.user.UserType;
import models.user.UserModel;

public class AdminRole extends Role{
    public AdminRole(UserModel user) {
        super(user, UserType.ADMINISTRATOR);
    }
}
