package graphics.colored.controllers.forms;

import app.User;
import graphics.GraphicsController;
import graphics.colored.Icon;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import graphics.colored.controllers.notifications.ScreenColoredGenericNotification;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import locales.Locales;
import utils.Utils;

/**
 * Class that represents the login form.
 */
public class ScreenColoredLoginForm extends ScreenColoredForm {

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

    /** TextField limits */
    private int minUsernameLength = 5;
    private int maxUsernameLength = 20;
    private int minPasswordLength = 5;
    private int maxPasswordLength = 20;


    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredLoginForm(PageController parentController) {
        super(Page.REGISTER_FORM, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels and the button click events.
     * I also set the TextFormatters per the text fields.
     */
    @FXML
    public void initialize() {

        title_label.setText(Locales.get("register"));
        username_text_field.setPromptText(Locales.get("username"));
        password_text_field.setPromptText(Locales.get("password"));
        username_prompt.setText(String.format(Locales.get("register_page_username_field_prompt"), minUsernameLength, maxUsernameLength));
        password_prompt.setText(String.format(Locales.get("register_page_password_field_prompt"), minPasswordLength, maxPasswordLength));
        login_button.setOnAction(event -> onLoginButtonClicked());

        username_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxUsernameLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

        password_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxPasswordLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));
    }

    /**
     * This function makes the user login. It checks that the user details are acceptable and then it
     * uses the user instance to perform log in
     */
    @FXML
    public void onLoginButtonClicked() {

        if (username_text_field.getText().length() < minUsernameLength) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_username_too_short"), Icon.ERROR);
            notification.display();
            return;
        }

        if (password_text_field.getText().length() < minPasswordLength) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_password_too_short"), Icon.ERROR);
            notification.display();
            return;
        }

        if (username_text_field.getText().length() > maxUsernameLength) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_username_too_long"), Icon.ERROR);
            notification.display();
            return;
        }

        if (password_text_field.getText().length() > maxPasswordLength) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_password_too_long"), Icon.ERROR);
            notification.display();
            return;
        }

        User.getInstance().login(username_text_field.getText(), password_text_field.getText());
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
}
