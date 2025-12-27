package entities.user;

import communication.dtos.user.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUserStudentEntity {

    @Test
    void all() {

        UserStudentEntity user = new UserStudentEntity();

        user.setCoins(1000);

        assertEquals(1000, user.getCoins());
        assertEquals(UserType.STUDENT, user.getUserType());
    }

}