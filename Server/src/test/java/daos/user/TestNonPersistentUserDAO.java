package daos.user;

import communication.dtos.user.UserType;
import entities.user.UserEntity;
import entities.user.UserStudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestNonPersistentUserDAO {

    private UserDAO userDAO;
    private UserEntity testUser;

    @BeforeEach
    void setup() {
        userDAO = new NonPersistentUserDAO();
        testUser = new UserStudentEntity();
        testUser.setUserType(UserType.STUDENT);
        testUser.setUsername("TMO");
        testUser.setToken("123");
    }

    @Test
    void saveUser() {
        userDAO.saveUser(testUser);

        assertNotNull(userDAO.findUserByToken("123"));
        assertNotNull(userDAO.findUserByUsername("TMO"));

        assertNull(userDAO.findUserByToken("456"));
        assertNull(userDAO.findUserByUsername("TMOOO"));
    }
}