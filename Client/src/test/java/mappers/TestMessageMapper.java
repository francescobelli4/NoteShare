package mappers;

import communication.dtos.message.MessageDTO;
import communication.dtos.message.MessageType;
import models.messages.MessageModel;
import org.junit.jupiter.api.Test;
import views.Icon;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestMessageMapper {

    private MessageDTO genMockedDTO() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle("Title");
        messageDTO.setDescription("Description");
        messageDTO.setDate("Date");
        messageDTO.setType(MessageType.INFO);
        return messageDTO;
    }

    @Test
    void toModel() {

        MessageModel model = MessageMapper.toModel(genMockedDTO());

        assertEquals("Title", model.getTitle());
        assertEquals("Description", model.getDescription());
        assertEquals("Date", model.getDate());
        assertEquals(Icon.APPICON, model.getIcon());
    }

    @Test
    void toModelList() {
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            messageDTOs.add(genMockedDTO());
        }

        List<MessageModel> models = MessageMapper.toModelList(messageDTOs);

        assertEquals(5, models.size());

        for (MessageModel model : models) {
            assertEquals("Title", model.getTitle());
            assertEquals("Description", model.getDescription());
            assertEquals("Date", model.getDate());
            assertEquals(Icon.APPICON, model.getIcon());
        }
    }
}