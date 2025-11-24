package models;

import communication.user.UserType;

public class AdminUserModel extends UserModel {

    public AdminUserModel() {
        setUserType(UserType.ADMINISTRATOR);
    }
}
