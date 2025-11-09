package persistency.shared.mappers;

import persistency.dtos.MessageDTO;
import persistency.shared.entities.MessageEntity;
import utils.MessageType;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    private MessageMapper() {}

    public static MessageDTO toDTO(MessageEntity messageEntity) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(messageEntity.getTitle());
        messageDTO.setDescription(messageEntity.getDescription());
        messageDTO.setDate(messageEntity.getDate());
        messageDTO.setType(MessageType.fromString(messageEntity.getType()));

        return messageDTO;
    }

    public static List<MessageDTO> toDTOList(List<MessageEntity> messages) {

        if (messages == null) return null;

        List<MessageDTO> messageDTOS = new ArrayList<>();

        for (MessageEntity messageEntity : messages) {
            messageDTOS.add(toDTO(messageEntity));
        }

        return messageDTOS;
    }
}
