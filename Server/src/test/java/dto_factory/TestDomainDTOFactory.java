package dto_factory;

import communication.dtos.message.MessageDTO;
import communication.dtos.message.MessageType;
import communication.dtos.user.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TestDomainDTOFactory {

    @Test
    void createUserDTO_Student() {

        UserDTO user = DomainDTOFactory.createUserDTO("TMO", UserType.STUDENT);

        assertInstanceOf(UserStudentDTO.class, user);
        assertEquals("TMO", user.getUsername());
        assertEquals(UserType.STUDENT, user.getUserType());
        assertEquals(0, ((UserStudentDTO)user).getCoins());
    }

    @Test
    void createUserDTO_Teacher() {

        UserDTO user = DomainDTOFactory.createUserDTO("TMO", UserType.TEACHER);
        assertInstanceOf(UserTeacherDTO.class, user);
    }

    @Test
    void createUserDTO_Admin() {

        UserDTO user = DomainDTOFactory.createUserDTO("TMO", UserType.ADMINISTRATOR);
        assertInstanceOf(UserAdminDTO.class, user);
    }

    @Test
    void createMessageDTO() {

        MessageDTO message = DomainDTOFactory.createMessageDTO("Title", "Date", "Description", MessageType.INFO);

        assertEquals("Title", message.getTitle());
        assertEquals("Description", message.getDescription());
        assertEquals("Date", message.getDate());
        assertEquals(MessageType.INFO, message.getType());
    }
}