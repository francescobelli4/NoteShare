package models.user.roles;

import communication.dtos.user.UserType;
import models.user.UserModel;

public class StudentRole extends Role {

    private int coins = 0;

    public StudentRole(UserModel user) {
        super(user, UserType.STUDENT);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
