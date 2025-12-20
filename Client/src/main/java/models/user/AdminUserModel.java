package models.user;

import communication.dtos.user.UserType;

public class AdminUserModel extends UserModel {

    public AdminUserModel() {
        setUserType(UserType.ADMINISTRATOR);
    }
}
