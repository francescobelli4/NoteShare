package app.mvc.viewmessages;

import app.mvc.Boundary;
import app.mvc.mappers.MessageMapper;
import app.mvc.models.MessageModel;
import persistency.dtos.MessageDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This boundary should allow communication between the user and the View Message use case.
 */
public class ViewMessagesBoundary extends Boundary {

    /**
     * The list of listeners that will be notified when events occur
     */
    private final ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Base constructor
     *
     * This should just call the superclass' constructor
     * @param controller the associated controller
     */
    public ViewMessagesBoundary(ViewMessagesController controller) {
        super(controller);
    }

    /**
     * This function should add a new listener
     * @param listener the new listener
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * This function should remove a listener from the list
     * @param listener the listener that has to be removed
     */
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    /**
     * This function should call the controller to set the list of messages received from
     * the server.
     * @param messages the list of message DTOs received from the server
     */
    public void messagesListArrived(List<MessageDTO> messages) {
        getController().setMessagesList(messages);
    }

    /**
     * This function should call the controller to add a new received messages to the list
     * and then it notifies the listeners
     * @param message the received message DTO
     */
    public void messageArrived(MessageDTO message) {
        getController().addMessage(message);

        for (Listener l : listeners) {
            l.onMessageArrived(MessageMapper.toModel(message));
        }
    }

    /**
     * This function should destroy the associated controller. The reference to this boundary
     * is deleted by the BoundaryManager.
     */
    @Override
    public void destroy() {
        controller = null;
    }

    /**
     * This function should return the correct subclass of this boundary's controller
     * @return a ViewMessageController reference
     */
    @Override
    protected ViewMessagesController getController() {
        return (ViewMessagesController) controller;
    }

    /**
     * The interface that all the listeners will implement to get notified on application
     * status changes
     */
    public interface Listener {
        void onMessageArrived(MessageModel message);
    }
}

