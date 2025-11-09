package app.mvc.viewmessages;

import app.mvc.Controller;
import app.mvc.mappers.MessageMapper;
import app.mvc.models.UserModel;
import persistency.dtos.MessageDTO;

import java.util.List;

/**
 * This controller should manage everything in the "View Messages" use case.
 * Its purpose is to divide the UI from the app functionalities' logic.
 */
public class ViewMessagesController extends Controller {

    /**
     * This function should set the list of messages in the UserModel
     * @param messages the list of messages
     */
    public void setMessagesList(List<MessageDTO> messages) {
        UserModel.getInstance().setMessages(MessageMapper.toModelList(messages));
    }

    /**
     * This function should add the new message to the list
     * @param message the new message
     */
    public void addMessage(MessageDTO message) {
        UserModel.getInstance().getMessages().add(MessageMapper.toModel(message));
    }

    /**
     * This function should return the correct Boundary subclass for this controller
     * @return a ViewMessagesBoundary reference
     */
    @Override
    protected ViewMessagesBoundary getBoundary() {
        return (ViewMessagesBoundary) boundary;
    }
}
