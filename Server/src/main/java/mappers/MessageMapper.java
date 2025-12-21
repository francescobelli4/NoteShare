package mappers;

import communication.dtos.message.MessageDTO;
import entities.message.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    private MessageMapper() {}

    public static MessageDTO toDTO(MessageEntity messageEntity) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(messageEntity.getTitle());
        messageDTO.setDescription(messageEntity.getDescription());
        messageDTO.setDate(messageEntity.getDate());
        messageDTO.setType(messageEntity.getType());

        return messageDTO;
    }

    public static List<MessageDTO> toDTOList(List<MessageEntity> messages) {

        List<MessageDTO> messageDTOS = new ArrayList<>();

        for (MessageEntity messageEntity : messages) {
            messageDTOS.add(toDTO(messageEntity));
        }

        return messageDTOS;
    }

    public static MessageEntity toEntity(MessageDTO messageDTO, String username) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setTitle(messageDTO.getTitle());
        messageEntity.setDate(messageDTO.getDate());
        messageEntity.setDescription(messageDTO.getDescription());
        messageEntity.setType(messageDTO.getType());
        messageEntity.setUsername(username);

        return messageEntity;
    }
}
