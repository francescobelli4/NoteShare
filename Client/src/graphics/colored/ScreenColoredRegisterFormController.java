package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import locales.Locales;
import messages.requests.RegisterMessage;
import user.User;
import utils.Utils;

import java.util.Map;

public class ScreenColoredRegisterFormController implements PageController{


    private final int minUsernameLength = 5;
    private final int minPasswordLength = 8;
    private final int maxUsernameLength = 15;
    private final int maxPasswordLength = 20;

    @FXML
    Label title_label;

    @FXML
    TextField username_text_field;

    @FXML
    PasswordField password_text_field;

    @FXML
    Label username_prompt;

    @FXML
    Label password_prompt;

    @FXML
    RadioButton student_radiobutton;

    @FXML
    RadioButton teacher_radiobutton;

    @FXML
    Label student_label;

    @FXML
    Label teacher_label;

    @FXML
    Button register_button;

    @Override
    public void setParams(Map<String, String> params) {}

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

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

        username_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxUsernameLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));

        password_text_field.setTextFormatter(new TextFormatter<String>(change -> {
            int len = change.getControlNewText().length();
            return len < maxPasswordLength && Utils.isAlphanumeric(change.getText()) ? change : null;
        }));
    }

    @FXML
    public void onStudentButtonClick() {
        teacher_radiobutton.setSelected(false);
    }

    @FXML
    public void onTeacherButtonClick() {
        student_radiobutton.setSelected(false);
    }

    @FXML
    public void onRegisterButtonClick() {

        if (username_text_field.getText().length() < minUsernameLength) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_username_too_short"), Icons.ERROR);
            return;
        }

        if (password_text_field.getText().length() < minPasswordLength) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_password_too_short"), Icons.ERROR);
            return;
        }

        if (username_text_field.getText().length() > maxUsernameLength) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_username_too_long"), Icons.ERROR);
            return;
        }

        if (password_text_field.getText().length() > maxPasswordLength) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_password_too_long"), Icons.ERROR);
            return;
        }

        if (!student_radiobutton.isSelected() && !teacher_radiobutton.isSelected()) {
            GraphicsController.displayNotification(Locales.get("error"), Locales.get("error_user_type_not_selected"), Icons.ERROR);
            return;
        }

        User.getInstance().register(username_text_field.getText(), password_text_field.getText(), student_radiobutton.isSelected() ? "student" : "teacher");

        //User.getInstance().enqueueMessage(new RegisterMessage(username_text_field.getText(), password_text_field.getText(), student_radiobutton.isSelected() ? "student" : "teacher"));
    }
}
