package communication.dtos.notification.message;

import communication.dtos.message.MessageDTO;

/**
 * This message is sent from server to client to notify the user that a new message was
 * sent to him.
 *
 * It sends a MessageDTO, so the client can display the notification and save the message in the
 * messages list.
 */
public class MessageNotificationDTO {

    private final MessageDTO message;

    public MessageNotificationDTO(MessageDTO message) {

        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }
}
