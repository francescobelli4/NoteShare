package daos.user;

import entities.UserEntity;

public interface UserDAO {

    void saveUser(UserEntity userEntity);
    UserEntity findUserByUsername(String username);
    UserEntity findUserByToken(String token);
}
