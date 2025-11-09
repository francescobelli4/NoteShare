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

    public void setMessagesList(List<MessageDTO> messages) {
        UserModel.getInstance().setMessages(MessageMapper.toModelList(messages));
    }

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
