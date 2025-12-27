package daos.message;

import entities.message.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestNPMessageDAO {

    private MessageDAO messageDAO;

    @BeforeEach
    void setup() {
        messageDAO = new NPMessageDAO();
    }

    @Test
    void save() {
        MessageEntity mockedMessage = mock(MessageEntity.class);
        when(mockedMessage.getUsername()).thenReturn("TMO");

        messageDAO.save(mockedMessage);

        assertTrue(messageDAO.get("TMO").contains(mockedMessage));

        assertNull(messageDAO.get("TMOOOO"));
    }
}