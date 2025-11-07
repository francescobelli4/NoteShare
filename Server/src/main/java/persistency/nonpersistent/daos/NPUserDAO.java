package persistency.nonpersistent.daos;

import persistency.shared.daos.UserDAO;
import persistency.shared.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Singleton
 *
 * This is the non-persistent memory implementation of UserDAO.
 * It's used when the app is launched in demo mode.
 */
public class NPUserDAO implements UserDAO {

    private static NPUserDAO instance;

    private final List<UserEntity> users = new ArrayList<>();

    public static NPUserDAO getInstance() {

        if (instance == null) {
            instance = new NPUserDAO();
        }

        return instance;
    }

    private NPUserDAO() {};

    @Override
    public boolean saveUser(UserEntity user) {

        if (findUserByUsername(user.getUsername()) != null) {
            return false;
        }

        users.add(user);
        System.out.println("User " + user.getUsername() + " saved!");

        return true;
    }

    /**
     * Not needed in demo mode :D
     */
    @Override
    public void updateUser(UserEntity user) {}


    @Override
    public UserEntity findUserByUsername(String username) {
        for (UserEntity user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }

        return null;
    }


    @Override
    public UserEntity findUserByToken(String token) {

        for (UserEntity user : users) {
            if (Objects.equals(user.getToken(), token)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return users;
    }
}
