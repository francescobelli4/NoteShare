package communication.dtos.notification.message;

import communication.dtos.message.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class MessagesNotificationDTO {

    private final List<MessageDTO> messages = new ArrayList<>();

    public MessagesNotificationDTO(List<MessageDTO> messages) {
        this.messages.addAll(messages);
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }
}