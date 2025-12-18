package graphics_controllers.access;

import graphics_controllers.GraphicsController;
import views.ViewFactory;
import views.access.AccessView;

public class AccessViewController extends GraphicsController<AccessView> {

    public AccessViewController(AccessView view) {
        super(view);
    }

    @Override
    public void loaded() {
        getView().loadForm(ViewFactory.getInstance().createAccessFormView().getRoot());
    }

}
