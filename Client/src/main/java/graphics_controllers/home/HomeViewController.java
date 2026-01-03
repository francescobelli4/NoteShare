package graphics_controllers.home;

import app_controllers.SearchController;
import graphics_controllers.GraphicsController;
import javafx.geometry.Bounds;
import javafx.scene.control.TextFormatter;
import utils.Utils;
import views.ViewFactory;
import views.ViewNavigator;
import views.home.HomeView;
import views.home.folders_container.FoldersContainerView;
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
        loadFoldersContainer();
        getView().getMessagesButton().setOnMouseClicked(_ -> messagesButtonClicked());

        getView().getSearchBar().textProperty().addListener((_, _, newV) -> {
            SearchController.updateQuery(newV);
        });
    }

    private void loadLeftBar() {
        LeftBarView leftBar = ViewFactory.getInstance().createLeftBarView();
        getView().appendLeftBar(leftBar.getRoot());
    }

    private void loadToolsBar() {
        ToolsBarView toolsBar = ViewFactory.getInstance().createToolsBarView();
        getView().appendToolsBar(toolsBar.getRoot());
    }

    private void loadFoldersContainer() {
        FoldersContainerView foldersContainer = ViewFactory.getInstance().createFoldersContainerView();
        getView().appendFoldersContainer(foldersContainer.getRoot());
    }

    private void messagesButtonClicked() {
        Bounds boundsInScene = getView().getMessagesButton().localToScene(getView().getMessagesButton().getBoundsInLocal());
        MessagesContainerView messagesContainerView = ViewFactory.getInstance().createMessagesContainerView(ViewNavigator.getStage().getWidth() - boundsInScene.getMinX(), boundsInScene.getMinY() + getView().getMessagesButton().getHeight());
        getView().appendMessagesContainer(messagesContainerView.getRoot());
    }
}
