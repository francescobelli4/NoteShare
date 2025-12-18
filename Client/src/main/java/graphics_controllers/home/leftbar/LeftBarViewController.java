package graphics_controllers.home.leftbar;

import graphics_controllers.GraphicsController;
import views.home.leftbar.LeftBarView;

public class LeftBarViewController extends GraphicsController<LeftBarView> {

    public LeftBarViewController(LeftBarView view) {
        super(view);
    }

    @Override
    public void loaded() {

        getView().appendMenuOptions();
        getView().appendUserData();
    }

}
