package communication.dtos.user;

public class UserStudentDTO extends UserDTO {

    private int coins;

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }
}
