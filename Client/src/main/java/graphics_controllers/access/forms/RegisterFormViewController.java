package graphics_controllers.access.forms;

import app_controllers.RegisterController;
import communication.user.UserType;
import exceptions.RegisterFailureException;
import graphics_controllers.GraphicsController;
import locales.Locales;
import views.Icon;
import views.ViewFactory;
import views.ViewNavigator;
import views.access.AccessView;
import views.access.forms.RegisterFormView;

import java.net.SocketException;

public class RegisterFormViewController extends GraphicsController<RegisterFormView> {

    private UserType userType = UserType.STUDENT;

    public RegisterFormViewController(RegisterFormView view) {
        super(view);
    }

    @Override
    public void loaded() {

        getView().getBackButton().setOnMouseClicked(_ -> backButtonClicked());
        getView().getStudentRadioButton().setOnMouseClicked(_ -> userTypeButtonClicked(UserType.STUDENT));
        getView().getTeacherRadioButton().setOnMouseClicked(_ -> userTypeButtonClicked(UserType.TEACHER));
        getView().getRegisterButton().setOnMouseClicked(_ -> registerButtonClicked());
    }

    private void backButtonClicked() {
        AccessView accessView = (AccessView)ViewNavigator.getActiveView();
        accessView.loadForm(ViewFactory.getInstance().createAccessFormView().getRoot());
    }

    private void userTypeButtonClicked(UserType userType) {

        getView().getStudentRadioButton().setSelected(userType == UserType.STUDENT);
        getView().getTeacherRadioButton().setSelected(userType == UserType.TEACHER);

        this.userType = userType;
    }

    private void registerButtonClicked() {

        try {
            RegisterController.register(getView().getUsernameTextField().getText(), getView().getPasswordTextField().getText(), userType);
        } catch (RegisterFailureException e) {

            String description = switch (e.getRegisterFailureReason()) {
                case USERNAME_TOO_SHORT -> Locales.get("error_username_too_short");
                case USERNAME_TOO_LONG -> Locales.get("error_username_too_long");
                case PASSWORD_TOO_SHORT -> Locales.get("error_password_too_short");
                case PASSWORD_TOO_LONG -> Locales.get("error_password_too_long");
                case USERNAME_ALREADY_TAKEN -> Locales.get("error_username_already_in_use");
            };

            ViewNavigator.displayNotification(Locales.get("error"), description, Icon.ERROR);
        } catch (SocketException _) {
            ViewNavigator.displayNotification(Locales.get("error"), Locales.get("communication_failed"), Icon.ERROR);
        }
    }
}
