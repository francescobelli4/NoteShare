package daos.user;

import entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NonPersistentUserDAO implements UserDAO{

    List<UserEntity> users = new ArrayList<>();

    /**
     * This function should only add a new UserEntity to users because this DAO is used
     * for the demo version of the app.
     * @param userEntity the user that has to be saved
     */
    @Override
    public void saveUser(UserEntity userEntity) {
        users.add(userEntity);
    }

    /**
     * This function should find a user in users by his username.
     * @param username the user's username
     * @return a UserEntity instance or null
     */
    @Override
    public UserEntity findUserByUsername(String username) {

        for (UserEntity user : users) {

            if (Objects.equals(user.getUsername(), username))
                return user;
        }

        return null;
    }

    /**
     * This function should find a user in users by his token.
     * @param token the user's token
     * @return a UserEntity instance or null
     */
    @Override
    public UserEntity findUserByToken(String token) {
        for (UserEntity user : users) {

            if (Objects.equals(user.getToken(), token))
                return user;
        }

        return null;
    }
}
