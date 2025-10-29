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
public class ScreenColoredRegisterForm extends ScreenColoredForm {

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
    RadioButton student_radiobutton;
    @FXML
    Label student_label;
    @FXML
    RadioButton teacher_radiobutton;
    @FXML
    Label teacher_label;
    @FXML
    Button register_button;

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
    public ScreenColoredRegisterForm(PageController parentController) {
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
        register_button.setText(Locales.get("register"));
        username_prompt.setText(String.format(Locales.get("register_page_username_field_prompt"), minUsernameLength, maxUsernameLength));
        password_prompt.setText(String.format(Locales.get("register_page_password_field_prompt"), minPasswordLength, maxPasswordLength));
        student_label.setText(Locales.get("student"));
        teacher_label.setText(Locales.get("teacher"));
        student_radiobutton.setOnAction(event -> onStudentButtonClick());
        teacher_radiobutton.setOnAction(event -> onTeacherButtonClick());
        register_button.setOnAction(event -> onRegisterButtonClick());

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
     * This function sets the user type.
     */
    @FXML
    public void onStudentButtonClick() {
        teacher_radiobutton.setSelected(false);
    }

    /**
     * This function sets the user type.
     */
    @FXML
    public void onTeacherButtonClick() {
        student_radiobutton.setSelected(false);
    }

    /**
     * This function makes the user register. It checks that the user details are acceptable and then it
     * uses the user instance to send the register request
     */
    @FXML
    public void onRegisterButtonClick() {

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

        if (!student_radiobutton.isSelected() && !teacher_radiobutton.isSelected()) {
            ScreenColoredGenericNotification notification = new ScreenColoredGenericNotification(Locales.get("error"), Locales.get("error_user_type_not_selected"), Icon.ERROR);
            notification.display();
            return;
        }

        User.getInstance().register(username_text_field.getText(), password_text_field.getText(), student_radiobutton.isSelected() ? "student" : "teacher");
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
