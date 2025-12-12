package communication.user;

public class UserDTOFactory {

    private UserDTOFactory() {}

    public static UserStudentDTO createUserStudentDTO(String username, int coins, UserType userType) {
        UserStudentDTO userStudentDTO = new UserStudentDTO();
        userStudentDTO.setUsername(username);
        userStudentDTO.setCoins(coins);
        userStudentDTO.setUserType(userType);
        return userStudentDTO;
    }
}
