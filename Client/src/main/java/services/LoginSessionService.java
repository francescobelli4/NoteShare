package services;

import models.UserModel;

public class LoginSessionService {

    private LoginSessionService() {}

    private static UserModel sessionUser;

    public static void setSessionUser(UserModel uniqueUser) {
        sessionUser = uniqueUser;
    }

    public static UserModel getSessionUser() {
        return sessionUser;
    }
}
