package graphics_controllers.home.messages;

import graphics_controllers.GraphicsController;
import views.home.messages.MessageView;

public class MessageViewController extends GraphicsController<MessageView> {

    public MessageViewController(MessageView view) {
        super(view);
    }

    @Override
    public void loaded() {
        //Nothing to do...
    }

}
