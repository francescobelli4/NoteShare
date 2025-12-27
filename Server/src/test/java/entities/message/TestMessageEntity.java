package entities.message;

import communication.dtos.message.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMessageEntity {

    @Test
    void all() {
        // 1. ARRANGE
        MessageEntity entity = new MessageEntity();

        entity.setTitle("Title");
        entity.setDescription("Description");
        entity.setDate("Date");
        entity.setUsername("Username");
        entity.setType(MessageType.INFO);

        assertEquals("Title", entity.getTitle());
        assertEquals("Description", entity.getDescription());
        assertEquals("Date", entity.getDate());
        assertEquals("Username", entity.getUsername());
        assertEquals(MessageType.INFO, entity.getType());
    }
}