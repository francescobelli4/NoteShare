package views.colored.forms;

import app.mvc.BoundaryManager;
import app.mvc.login.LoginResult;
import app.mvc.login.LoginBoundary;
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
public class ScreenColoredLoginForm extends ScreenColoredForm implements LoginBoundary.Listener {

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
    Button loginButton;

    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredLoginForm(PageController parentController) {
        super(Page.LOGIN_FORM, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);

        BoundaryManager.getInstance().initializeLoginBoundary();
        BoundaryManager.getInstance().getLoginBoundary().addListener(this);
    }

    /**
     * In the initialize function, I set all the labels and the button click events.
     * I also set the TextFormatters per the text fields.
     */
    @FXML
    public void initialize() {

        titleLabel.setText(Locales.get("login"));
        usernameTextField.setPromptText(Locales.get("username"));
        passwordTextField.setPromptText(Locales.get("password"));
        usernamePrompt.setText(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()));
        passwordPrompt.setText(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()));
        loginButton.setOnAction(_ -> onLoginButtonClicked());

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
     * This function makes the user login. It checks that the user details are acceptable and then it
     * uses the user instance to perform log in
     */
    @FXML
    public void onLoginButtonClicked() {
        BoundaryManager.getInstance().getLoginBoundary().performLogin(usernameTextField.getText(), passwordTextField.getText());
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }

    @Override
    public void onLoginSuccess() {
        Platform.runLater(() -> {
            ScreenColoredHomePage screenColoredHomePage = new ScreenColoredHomePage();
            screenColoredHomePage.display();
        });

        BoundaryManager.getInstance().destroyLoginBoundary();
    }

    @Override
    public void onLoginFailed(LoginResult loginResult) {

        String title = Locales.get("error");
        Icon icon = Icon.ERROR;
        String description = switch (loginResult) {
            case LoginResult.USERNAME_TOO_SHORT ->
                    Locales.get("error_username_too_short");
            case LoginResult.USERNAME_TOO_LONG ->
                    Locales.get("error_username_too_long");
            case LoginResult.PASSWORD_TOO_SHORT ->
                    Locales.get("error_password_too_short");
            case LoginResult.PASSWORD_TOO_LONG ->
                    Locales.get("error_password_too_long");
            case LoginResult.USER_NOT_EXISTS ->
                    Locales.get("error_user_does_not_exist");
            case LoginResult.WRONG_PASSWORD ->
                    Locales.get("error_wrong_password");
        };

        ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(title, description, icon);

        Platform.runLater(notification::display);
    }
}
