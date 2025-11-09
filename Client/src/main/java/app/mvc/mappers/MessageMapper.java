package app.mvc.mappers;

import app.mvc.models.MessageModel;
import persistency.dtos.MessageDTO;
import views.colored.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should only map MessageModel <---> MessageDTO
 */
public class MessageMapper {

    private MessageMapper() {}

    public static MessageModel toModel(MessageDTO message) {

        //TODO AGGIUNGERE LE GIUSTE ICONE PER OGNI CASO DELLO SWITCH... ORA NON MI VA DI FARE LE ICONE
        Icon icon;
        switch (message.getType()) {
            case NOTE_BOUGHT -> icon = Icon.SUCCESS;
            case NOTE_SOLD -> icon = Icon.ERROR;
            case INFO -> icon = Icon.APPICON;
            default -> icon = Icon.APPICON;
        }

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
