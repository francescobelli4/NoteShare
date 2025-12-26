package models.user;

import communication.dtos.user.UserType;

public class StudentUserModel extends UserModel {

    private int coins;

    public StudentUserModel() {
        setUserType(UserType.STUDENT);
    }

    public StudentUserModel(String username, UserType userType) {
        super(username, userType);
        setUserType(UserType.STUDENT);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
