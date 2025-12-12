package graphics_controllers;

import app_controllers.RegisterController;
import communication.user.UserType;
import exceptions.RegisterFailureException;
import locales.Locales;
import views.Icon;
import views.RegisterFormView;
import views.View;
import views.ViewNavigator;

import java.net.SocketException;

public class RegisterFormViewController extends GraphicsController<RegisterFormView> {

    private UserType userType = UserType.STUDENT;

    private Listener listener;

    public RegisterFormViewController(View view) {
        super(view);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void setup() {

        getView().studentRadioButton.setOnMouseClicked(_ -> studentButtonClicked());
        getView().teacherRadioButton.setOnMouseClicked(_ -> teacherButtonClicked());
        getView().backButton.setOnMouseClicked(_ -> backButtonClicked());
        getView().registerButton.setOnMouseClicked(_ -> registerButtonClicked());
    }

    private void backButtonClicked() {
        listener.backButtonClicked();
    }

    private void studentButtonClicked() {
        getView().teacherRadioButton.setSelected(false);
        getView().studentRadioButton.setSelected(true);
        userType = UserType.STUDENT;
    }

    private void teacherButtonClicked() {
        getView().studentRadioButton.setSelected(false);
        getView().teacherRadioButton.setSelected(true);
        userType = UserType.TEACHER;
    }

    private void registerButtonClicked() {

        try {
            RegisterController.register(getView().usernameTextField.getText(), getView().passwordTextField.getText(), userType);
        } catch (RegisterFailureException e) {
            String desc = switch (e.getRegisterFailureReason()) {
                case USERNAME_ALREADY_TAKEN -> Locales.get("error_username_already_in_use");
                case USERNAME_TOO_SHORT -> Locales.get("error_username_too_short");
                case USERNAME_TOO_LONG -> Locales.get("error_username_too_long");
                case PASSWORD_TOO_SHORT -> Locales.get("error_password_too_short");
                case PASSWORD_TOO_LONG -> Locales.get("error_password_too_long");
            };

            /**
             * BOH FORSE SAREBBE MEGLIO IMPLEMENTARLO NEL LISTENER L'ERRORE???????
             * DA VEDERE DAL PROFESSORE....... NON SO SE GLI VANNO BENE LE EXCEPTION PER
             * "ERRORI" DERIVANTI DALLA LOGICA APPLICATIVA
             */
            ViewNavigator.getInstance().displayNotification(Locales.get("error"), desc, Icon.ERROR);
        } catch (SocketException e) {
            ViewNavigator.getInstance().displayNotification(Locales.get("error"), Locales.get("communication_failed"), Icon.ERROR);
        }
    }

    public interface Listener {
        void backButtonClicked();
    }
}
