package views;

import views.access.AccessView;
import views.access.forms.AccessFormView;
import views.access.forms.LoginFormView;
import views.access.forms.RegisterFormView;
import views.home.HomeView;
import views.home.leftbar.LeftBarMenuOptionsView;
import views.home.leftbar.LeftBarUserDataView;
import views.home.leftbar.LeftBarView;
import views.notification.NotificationView;

public class ViewFactory {

    private static ViewFactory instance;
    public static ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory();
        }
        return instance;
    }
    private ViewFactory () {}

    public HomeView createHomeView() {
        return new HomeView();
    }

    public LeftBarView createLeftBarView() {
        return new LeftBarView();
    }

    public LeftBarMenuOptionsView createLeftBarMenuOptionsView() {
        return new LeftBarMenuOptionsView();
    }

    public LeftBarUserDataView createLeftBarUserDataView() {
        return new LeftBarUserDataView();
    }

    public AccessView createAccessView() {
        return new AccessView();
    }

    public AccessFormView createAccessFormView() {
        return new AccessFormView();
    }

    public RegisterFormView createRegisterFormView() {
        return new RegisterFormView();
    }

    public LoginFormView createLoginFormView() {
        return new LoginFormView();
    }

    public NotificationView createNotificationView(String title, String description, Icon icon) {
        return new NotificationView(title, description, icon);
    }
}