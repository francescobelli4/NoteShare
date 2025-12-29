package graphics_controllers.home.leftbar;

import app.AppContext;
import graphics_controllers.GraphicsController;
import models.user.UserModel;
import models.user.roles.StudentRole;
import views.ViewNavigator;
import views.home.leftbar.LeftBarUserDataView;

public class LeftBarUserDataViewController extends GraphicsController<LeftBarUserDataView> {

    public LeftBarUserDataViewController(LeftBarUserDataView view) {
        super(view);
    }

    @Override
    public void loaded() {

        UserModel user = AppContext.getInstance().getCurrentUser();
        boolean isLoggedIn = user.isLoggedIn();

        getView().getAccessForm().setManaged(!isLoggedIn);
        getView().getAccessForm().setVisible(!isLoggedIn);
        getView().getUserData().setManaged(isLoggedIn);
        getView().getUserData().setVisible(isLoggedIn);

        if (isLoggedIn) {
            getView().getUsernameLabel().setText(user.getUsername());
            user.as(StudentRole.class).ifPresent(student -> getView().getCoinsLabel().setText(student.getCoins()+""));
        }

        getView().getAccessButton().setOnMouseClicked(_ -> accessButtonClicked());
    }

    private void accessButtonClicked() {
        ViewNavigator.displayAccessView();
    }
}
