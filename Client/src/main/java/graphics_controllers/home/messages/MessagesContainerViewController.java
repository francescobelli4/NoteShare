package graphics_controllers.home.messages;

import graphics_controllers.GraphicsController;
import views.ViewFactory;
import views.home.HomeView;
import views.home.leftbar.LeftBarView;
import views.home.messages.MessagesContainerView;
import views.home.toolsbar.ToolsBarView;

public class MessagesContainerViewController extends GraphicsController<MessagesContainerView> {

    public MessagesContainerViewController(MessagesContainerView view) {
        super(view);
    }

    @Override
    public void loaded() {

    }

}
