package views.colored.forms;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.register.RegisterBoundary;
import app.mvc.register.RegisterResult;
import views.GraphicsController;
import views.colored.Icon;
import views.colored.Page;
import views.colored.PageController;
import views.colored.main_pages.ScreenColoredHomePage;
import views.colored.notifications.ScreenColoredGenericNotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import locales.Locales;
import utils.Utils;

/**
 * Class that represents the login form.
 */
public class ScreenColoredRegisterForm extends ScreenColoredForm implements RegisterBoundary.Listener {

    /**
     * FXML elements
     */
    @FXML
    Label titleLabel;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Label usernamePrompt;
    @FXML
    Label passwordPrompt;
    @FXML
    RadioButton studentRadioButton;
    @FXML
    Label studentLabel;
    @FXML
    RadioButton teacherRadioButton;
    @FXML
    Label teacherLabel;
    @FXML
    Button registerButton;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredRegisterForm(PageController parentController) {
        super(Page.REGISTER_FORM, parentController);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);

        BoundaryManager.getInstance().initializeRegisterBoundary();

        BoundaryManager.getInstance().getRegisterBoundary().addListener(this);
    }

    /**
     * In the initialize function, I set all the labels and the button click events.
     * I also set the TextFormatters per the text fields.
     */
    @FXML
    public void initialize() {

        titleLabel.setText(Locales.get("register"));
        usernameTextField.setPromptText(Locales.get("username"));
        passwordTextField.setPromptText(Locales.get("password"));
        registerButton.setText(Locales.get("register"));
        usernamePrompt.setText(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()));
        passwordPrompt.setText(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()));
        studentLabel.setText(Locales.get("student"));
        teacherLabel.setText(Locales.get("teacher"));
        studentRadioButton.setOnAction(_ -> onStudentButtonClick());
        teacherRadioButton.setOnAction(_ -> onTeacherButtonClick());
        registerButton.setOnAction(_ -> onRegisterButtonClick());

        TextFormatter<String> usernameTextFormatter = new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < Utils.getMaxUsernameLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        });

        TextFormatter<String> passwordTextFormatter = new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();
            return len < Utils.getMaxPasswordLength() && Utils.isAlphanumeric(change.getText()) ? change : null;
        });

        usernameTextField.setTextFormatter(usernameTextFormatter);
        passwordTextField.setTextFormatter(passwordTextFormatter);
    }

    /**
     * This function sets the user type.
     */
    @FXML
    public void onStudentButtonClick() {
        teacherRadioButton.setSelected(false);
    }

    /**
     * This function sets the user type.
     */
    @FXML
    public void onTeacherButtonClick() {
        studentRadioButton.setSelected(false);
    }

    /**
     * This function makes the user register. It checks that the user details are acceptable and then it
     * uses the user instance to send the register request
     */
    @FXML
    public void onRegisterButtonClick() {

        String userType = null;

        if (studentRadioButton.isSelected() && !teacherRadioButton.isSelected()) {
            userType = "student";
        } else if (teacherRadioButton.isSelected() && !studentRadioButton.isSelected()) {
            userType = "teacher";
        }

        BoundaryManager.getInstance().getRegisterBoundary().performRegister(usernameTextField.getText(), passwordTextField.getText(), userType);
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }

    @Override
    public void onRegisterSuccess() {
        Platform.runLater(() -> {
            ScreenColoredHomePage screenColoredHomePage = new ScreenColoredHomePage();
            screenColoredHomePage.display();
        });

        BoundaryManager.getInstance().destroyRegisterBoundary();
        BoundaryManager.getInstance().destroyLoginBoundary();
    }

    @Override
    public void onRegisterFailed(RegisterResult registerResult) {

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;

        String description = switch (registerResult) {
            case RegisterResult.USERNAME_TOO_SHORT ->
                    Locales.get("error_username_too_short");
            case RegisterResult.USERNAME_TOO_LONG ->
                    Locales.get("error_username_too_long");
            case RegisterResult.PASSWORD_TOO_SHORT ->
                    Locales.get("error_password_too_short");
            case RegisterResult.PASSWORD_TOO_LONG ->
                    Locales.get("error_password_too_long");
            case RegisterResult.USERNAME_ALREADY_IN_USE ->
                    Locales.get("error_username_already_in_use");
            case RegisterResult.USER_TYPE_NOT_SELECTED ->
                    Locales.get("error_user_type_not_selected");
        };

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(title, description, icon);

        Platform.runLater(notification::display);
    }
}
