package entities.user;

import communication.dtos.user.UserType;

public class UserStudentEntity extends UserEntity {

    private int coins;

    public UserStudentEntity() {
        this.setUserType(UserType.STUDENT);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
