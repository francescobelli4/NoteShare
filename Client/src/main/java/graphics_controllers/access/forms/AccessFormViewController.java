package graphics_controllers.access.forms;

import graphics_controllers.GraphicsController;
import views.ViewFactory;
import views.ViewNavigator;
import views.access.AccessView;
import views.access.forms.AccessFormView;

public class AccessFormViewController extends GraphicsController<AccessFormView> {

    public AccessFormViewController(AccessFormView view) {
        super(view);
    }

    @Override
    public void loaded() {

        getView().getLoginButton().setOnMouseClicked(_ -> loginButtonClicked());
        getView().getRegisterButton().setOnMouseClicked(_ -> registerButtonClicked());
    }

    private void loginButtonClicked() {
        AccessView accessView = (AccessView) ViewNavigator.getActiveView();
        accessView.loadForm(ViewFactory.getInstance().createLoginFormView().getRoot());
    }

    private void registerButtonClicked() {
        AccessView accessView = (AccessView) ViewNavigator.getActiveView();
        accessView.loadForm(ViewFactory.getInstance().createRegisterFormView().getRoot());
    }
}
