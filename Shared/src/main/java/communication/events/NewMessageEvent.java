package communication.events;

import communication.Transferable;
import persistency.dtos.MessageDTO;

/**
 * This message is sent from server to client to notify the user that a new message was
 * sent to him.
 *
 * It sends a MessageDTO, so the client can display the notification and save the message in the
 * messages list.
 */
public class NewMessageEvent extends Transferable {

    private final MessageDTO message;

    public NewMessageEvent(MessageDTO message) {

        id = 5;
        this.message = message;
    }

    public MessageDTO getMessage() {
        return message;
    }
}
