package models;

public class StudentUserModel extends UserModel {

    private static StudentUserModel userModel;
    private int coins;

    private StudentUserModel() {
        setUserType("student");
    }

    public static StudentUserModel getInstance() {

        if (userModel == null) {
            userModel = new StudentUserModel();
        }

        return userModel;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void login() {

    }
}
