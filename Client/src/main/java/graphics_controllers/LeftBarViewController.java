package graphics_controllers;

import sessions.UserSession;
import views.LeftBarView;
import views.View;
import views.ViewNavigator;

public class LeftBarViewController extends GraphicsController<LeftBarView> {

    public LeftBarViewController(View view) {
        super(view);
    }

    @Override
    public void setup() {

        if (UserSession.getInstance().getCurrentUser() != null) {
            getView().setUserDataComponent();
        } else {
            getView().setAccessComponent();
            getView().getAccessButton().setOnMouseClicked(_ -> ViewNavigator.getInstance().displayAccessView());
        }
    }
}
