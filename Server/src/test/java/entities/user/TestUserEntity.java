package entities.user;

import communication.dtos.user.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUserEntity {

    @Test
    void all() {

        UserEntity entity = new UserEntity();

        entity.setUsername("Username");
        entity.setPassword("Password");
        entity.setToken("Token");
        entity.setUserType(UserType.STUDENT);

        assertEquals("Username", entity.getUsername());
        assertEquals("Password", entity.getPassword());
        assertEquals("Token", entity.getToken());
        assertEquals(UserType.STUDENT, entity.getUserType());
    }
}