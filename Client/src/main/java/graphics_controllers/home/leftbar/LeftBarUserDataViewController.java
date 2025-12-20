package graphics_controllers.home.leftbar;

import app.App;
import communication.dtos.user.UserType;
import graphics_controllers.GraphicsController;
import models.user.StudentUserModel;
import views.ViewNavigator;
import views.home.leftbar.LeftBarUserDataView;

public class LeftBarUserDataViewController extends GraphicsController<LeftBarUserDataView> {

    public LeftBarUserDataViewController(LeftBarUserDataView view) {
        super(view);
    }

    @Override
    public void loaded() {

        boolean isLoggedIn = App.getUser().isLoggedIn();

        getView().getAccessForm().setManaged(!isLoggedIn);
        getView().getAccessForm().setVisible(!isLoggedIn);
        getView().getUserData().setManaged(isLoggedIn);
        getView().getUserData().setVisible(isLoggedIn);

        if (isLoggedIn) {
            getView().getUsernameLabel().setText(App.getUser().getUsername());

            if (App.getUser().getUserType() == UserType.STUDENT)
                getView().getCoinsLabel().setText(App.getUserAs(StudentUserModel.class).getCoins()+"");
        }

        getView().getAccessButton().setOnMouseClicked(_ -> accessButtonClicked());
    }

    private void accessButtonClicked() {
        ViewNavigator.displayAccessView();
    }
}
