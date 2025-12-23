package models.user;

import communication.dtos.user.UserType;

import java.util.ArrayList;

public class StudentUserModel extends UserModel {

    private int coins;

    public StudentUserModel() {
        setUserType(UserType.STUDENT);
    }

    public StudentUserModel(String username, UserType userType) {
        super(username, userType);
        setUserType(UserType.STUDENT);
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;

        if (loggedIn) {
            for (LoginListener l : new ArrayList<>(super.getLoginListeners())) {
                l.onLoggedIn();
            }
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
