package mappers;

import communication.dtos.message.MessageDTO;
import communication.dtos.message.MessageType;
import entities.message.MessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestMessageMapper {

    private MessageEntity messageEntity;
    private MessageDTO messageDTO;

    @BeforeEach
    void setup() {
        messageEntity = new MessageEntity();
        messageEntity.setTitle("Title");
        messageEntity.setDescription("Description");
        messageEntity.setDate("Date");
        messageEntity.setUsername("TMO");
        messageEntity.setType(MessageType.INFO);

        messageDTO = new MessageDTO();
        messageDTO.setTitle("Title");
        messageDTO.setDescription("Description");
        messageDTO.setDate("Date");
        messageDTO.setType(MessageType.INFO);
    }

    @Test
    void toDTO() {
        MessageDTO res = MessageMapper.toDTO(messageEntity);

        assertEquals("Title", res.getTitle());
        assertEquals("Description", res.getDescription());
        assertEquals("Date", res.getDate());
        assertEquals(MessageType.INFO, res.getType());
    }

    @Test
    void toDTOList() {

        List<MessageEntity> l = new ArrayList<>();
        l.add(messageEntity);

        List<MessageDTO> res = MessageMapper.toDTOList(l);

        assertFalse(res.isEmpty());
        assertNotNull(res.getFirst());

        assertEquals("Title", res.getFirst().getTitle());
        assertEquals("Description", res.getFirst().getDescription());
        assertEquals("Date", res.getFirst().getDate());
        assertEquals(MessageType.INFO, res.getFirst().getType());
    }

    @Test
    void toEntity() {

        MessageEntity res = MessageMapper.toEntity(messageDTO, "TMO");

        assertEquals("Title", res.getTitle());
        assertEquals("Description", res.getDescription());
        assertEquals("Date", res.getDate());
        assertEquals("TMO", res.getUsername());
        assertEquals(MessageType.INFO, res.getType());
    }
}