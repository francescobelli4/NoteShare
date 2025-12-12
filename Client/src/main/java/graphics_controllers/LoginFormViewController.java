package graphics_controllers;

import app_controllers.LoginController;
import app_controllers.RegisterController;
import communication.user.UserType;
import exceptions.LoginFailureException;
import exceptions.RegisterFailureException;
import locales.Locales;
import views.*;

import java.net.SocketException;

import static communication.dtos.responses.login.LoginFailureReason.*;
import static communication.dtos.responses.login.RegisterFailureReason.USERNAME_ALREADY_TAKEN;

public class LoginFormViewController extends GraphicsController<LoginFormView> {

    private Listener listener;

    public LoginFormViewController(View view) {
        super(view);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void setup() {

        getView().getBackButton().setOnMouseClicked(_ -> backButtonClicked());
        getView().getLoginButton().setOnMouseClicked(_ -> loginButtonClicked());
    }

    private void backButtonClicked() {
        listener.backButtonClicked();
    }

    private void loginButtonClicked() {

        try {
            LoginController.login(getView().getUsernameTextField().getText(), getView().getPasswordTextField().getText());
        } catch (LoginFailureException e) {
            String desc = switch (e.getLoginFailureReason()) {
                case WRONG_USERNAME -> Locales.get("error_user_does_not_exist");
                case WRONG_PASSWORD -> Locales.get("error_wrong_password");
                case USERNAME_TOO_SHORT -> Locales.get("error_username_too_short");
                case USERNAME_TOO_LONG -> Locales.get("error_username_too_long");
                case PASSWORD_TOO_SHORT -> Locales.get("error_password_too_short");
                case PASSWORD_TOO_LONG -> Locales.get("error_password_too_long");
                case WRONG_TOKEN -> null;
            };

            /**
             * BOH FORSE SAREBBE MEGLIO IMPLEMENTARLO NEL LISTENER L'ERRORE???????
             * DA VEDERE DAL PROFESSORE....... NON SO SE GLI VANNO BENE LE EXCEPTION PER
             * "ERRORI" DERIVANTI DALLA LOGICA APPLICATIVA
             */
            ViewNavigator.getInstance().displayNotification(Locales.get("error"), desc, Icon.ERROR);
        } catch (SocketException _) {
            ViewNavigator.getInstance().displayNotification(Locales.get("error"), Locales.get("communication_failed"), Icon.ERROR);
        }
    }

    public interface Listener {
        void backButtonClicked();
    }
}
