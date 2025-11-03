package graphics.colored.controllers.forms;

import app.bce.BoundaryManager;
import app.bce.login.LoginResult;
import app.bce.login.LoginBoundary;
import graphics.GraphicsController;
import graphics.colored.Icon;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import graphics.colored.controllers.main_pages.ScreenColoredHomePage;
import graphics.colored.controllers.notifications.ScreenColoredGenericNotification;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    Label title_label;
    @FXML
    TextField username_text_field;
    @FXML
    TextField password_text_field;
    @FXML
    Label username_prompt;
    @FXML
    Label password_prompt;
    @FXML
    Button login_button;

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

        BoundaryManager.getInstance().getLoginBoundary().addListener(this);
    }

    /**
     * In the initialize function, I set all the labels and the button click events.
     * I also set the TextFormatters per the text fields.
     */
    @FXML
    public void initialize() {

        title_label.setText(Locales.get("login"));
        username_text_field.setPromptText(Locales.get("username"));
        password_text_field.setPromptText(Locales.get("password"));
        username_prompt.setText(String.format(Locales.get("register_page_username_field_prompt"), BoundaryManager.getInstance().getLoginBoundary().getMIN_USERNAME_LENGTH(), BoundaryManager.getInstance().getLoginBoundary().getMAX_USERNAME_LENGTH()));
        password_prompt.setText(String.format(Locales.get("register_page_password_field_prompt"), BoundaryManager.getInstance().getLoginBoundary().getMIN_PASSWORD_LENGTH(), BoundaryManager.getInstance().getLoginBoundary().getMAX_PASSWORD_LENGTH()));
        login_button.setOnAction(_ -> onLoginButtonClicked());

        username_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < BoundaryManager.getInstance().getLoginBoundary().getMAX_USERNAME_LENGTH() && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

        password_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < BoundaryManager.getInstance().getLoginBoundary().getMAX_PASSWORD_LENGTH() && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));
    }

    /**
     * This function makes the user login. It checks that the user details are acceptable and then it
     * uses the user instance to perform log in
     */
    @FXML
    public void onLoginButtonClicked() {
        BoundaryManager.getInstance().getLoginBoundary().performLogin(username_text_field.getText(), password_text_field.getText());
    }


    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }

    /**
     * This function should actually show this page.
     * @param container the container that contains this page
     */
    @Override
    public void display(VBox container) {
        super.display(container);
    }

    @Override
    public void onLoginSuccess() {
        Platform.runLater(() -> {
            ScreenColoredHomePage screenColoredHomePage = new ScreenColoredHomePage();
            screenColoredHomePage.display();
        });
    }

    @Override
    public void onLoginFailed(LoginResult loginResult) {

        ScreenColoredGenericNotification notification = switch (loginResult) {
            case LoginResult.USERNAME_TOO_SHORT ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_username_too_short"), Icon.ERROR);
            case LoginResult.USERNAME_TOO_LONG ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_username_too_long"), Icon.ERROR);
            case LoginResult.PASSWORD_TOO_SHORT ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_password_too_short"), Icon.ERROR);
            case LoginResult.PASSWORD_TOO_LONG ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_password_too_long"), Icon.ERROR);
            case LoginResult.USER_NOT_EXISTS ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_user_does_not_exist"), Icon.ERROR);
            case LoginResult.WRONG_PASSWORD ->
                    new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_wrong_password"), Icon.ERROR);
        };

        Platform.runLater(notification::display);
    }
}
