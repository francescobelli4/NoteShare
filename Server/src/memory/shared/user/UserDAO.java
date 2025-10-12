package memory.shared.user;

import java.util.List;

/**
 * This DAO should define all the possible operations the server can
 * do with UserEntities.
 * The real implementation depends on the launch mode of the app (demo
 * or default).
 *
 * DEMO: NPUserDAO (non-persistent)
 * DEFAULT: PUserDAO (persistent)
 */
public interface UserDAO {


    boolean saveUser(UserEntity user);
    void updateUser(UserEntity user);

    UserEntity findUserByUsername(String username);
    UserEntity findUserByToken(String token);

    List<UserEntity> getAllUsers();
}
