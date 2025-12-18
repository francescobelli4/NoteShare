package graphics_controllers.access.forms;

import app_controllers.LoginController;
import exceptions.LoginFailureException;
import graphics_controllers.GraphicsController;
import locales.Locales;
import views.Icon;
import views.ViewFactory;
import views.ViewNavigator;
import views.access.AccessView;
import views.access.forms.LoginFormView;

import java.net.SocketException;

public class LoginFormViewController extends GraphicsController<LoginFormView> {

    public LoginFormViewController(LoginFormView view) {
        super(view);
    }

    @Override
    public void loaded() {

        getView().getBackButton().setOnMouseClicked(_ -> backButtonClicked());
        getView().getLoginButton().setOnMouseClicked(_ -> loginButtonClicked());
    }

    private void backButtonClicked() {
        AccessView accessView = (AccessView)ViewNavigator.getActiveView();
        accessView.loadForm(ViewFactory.getInstance().createAccessFormView().getRoot());
    }

    private void loginButtonClicked() {

        try {
            LoginController.login(getView().getUsernameTextField().getText(), getView().getPasswordTextField().getText());
        } catch (LoginFailureException e) {

            String description = switch (e.getLoginFailureReason()) {
                case USERNAME_TOO_SHORT -> Locales.get("error_username_too_short");
                case USERNAME_TOO_LONG -> Locales.get("error_username_too_long");
                case PASSWORD_TOO_SHORT -> Locales.get("error_password_too_short");
                case PASSWORD_TOO_LONG -> Locales.get("error_password_too_long");
                case WRONG_USERNAME -> Locales.get("error_user_does_not_exist");
                case WRONG_PASSWORD -> Locales.get("error_wrong_password");
                default -> null;
            };

            ViewNavigator.displayNotification(Locales.get("error"), description, Icon.ERROR);
        } catch (SocketException e) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("communication_failed"), Icon.ERROR);
        }
    }
}
