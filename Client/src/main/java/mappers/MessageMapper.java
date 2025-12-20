package mappers;

import communication.dtos.message.MessageDTO;
import models.messages.MessageModel;
import views.Icon;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public static MessageModel toModel(MessageDTO message) {

        Icon icon = switch (message.getType()) {
            case INFO -> Icon.APPICON;
            default -> Icon.NOTIFICATION;
        };

        return new MessageModel(message.getTitle(), message.getDescription(), message.getDate(), icon);
    }

    public static List<MessageModel> toModelList(List<MessageDTO> messages) {

        List<MessageModel> messagesList = new ArrayList<>();

        for (MessageDTO mess : messages) {
            messagesList.add(toModel(mess));
        }

        return messagesList;
    }
}
