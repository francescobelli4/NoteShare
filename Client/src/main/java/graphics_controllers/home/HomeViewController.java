package graphics_controllers.home;

import app.App;
import graphics_controllers.GraphicsController;
import javafx.geometry.Bounds;
import views.ViewFactory;
import views.ViewNavigator;
import views.home.HomeView;
import views.home.leftbar.LeftBarView;
import views.home.messages.MessagesContainerView;
import views.home.toolsbar.ToolsBarView;

public class HomeViewController extends GraphicsController<HomeView> {

    public HomeViewController(HomeView view) {
        super(view);
    }

    @Override
    public void loaded() {

        loadLeftBar();
        loadToolsBar();
        getView().getMessagesButton().setOnMouseClicked(_ -> messagesButtonClicked());
    }

    private void loadLeftBar() {

        LeftBarView leftBar = ViewFactory.getInstance().createLeftBarView();
        getView().appendLeftBar(leftBar.getRoot());
    }

    private void loadToolsBar() {
        ToolsBarView toolsBar = ViewFactory.getInstance().createToolsBarView();
        getView().appendToolsBar(toolsBar.getRoot());
    }

    private void messagesButtonClicked() {
        Bounds boundsInScene = getView().getMessagesButton().localToScene(getView().getMessagesButton().getBoundsInLocal());
        MessagesContainerView messagesContainerView = ViewFactory.getInstance().createMessagesContainerView(ViewNavigator.getStage().getWidth() - boundsInScene.getMinX(), boundsInScene.getMinY() + getView().getMessagesButton().getHeight());
        getView().appendMessagesContainer(messagesContainerView.getRoot());
    }
}
