package graphics_controllers;

import views.*;

public class HomeViewController extends GraphicsController<HomeView> {

    public HomeViewController(View view) {
        super(view);
    }

    @Override
    public void setup() {
        getView().setLeftBar(ViewFactory.getInstance().createLeftBarView().getRoot());
    }
}
