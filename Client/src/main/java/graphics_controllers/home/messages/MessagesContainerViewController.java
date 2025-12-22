package graphics_controllers.home.messages;

import graphics_controllers.GraphicsController;
import views.home.messages.MessagesContainerView;

public class MessagesContainerViewController extends GraphicsController<MessagesContainerView> {

    public MessagesContainerViewController(MessagesContainerView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().getRoot().setOnMouseClicked(_ -> getView().close());
    }
}
