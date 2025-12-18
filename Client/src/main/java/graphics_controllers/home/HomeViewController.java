package graphics_controllers.home;

import graphics_controllers.GraphicsController;
import views.ViewFactory;
import views.home.HomeView;
import views.home.leftbar.LeftBarView;

public class HomeViewController extends GraphicsController<HomeView> {

    public HomeViewController(HomeView view) {
        super(view);
    }

    @Override
    public void loaded() {

        loadLeftBar();

        getView().getMessagesButton().setOnMouseClicked(_ -> messagesButtonClicked());
    }

    private void loadLeftBar() {

        LeftBarView leftBar = ViewFactory.getInstance().createLeftBarView();
        getView().appendLeftBar(leftBar.getRoot());
    }

    private void messagesButtonClicked() {

    }
}
