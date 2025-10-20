package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import locales.Locales;
import messages.requests.LoginMessage;
import messages.requests.RegisterMessage;
import user.User;
import utils.Utils;

import java.util.Map;

public class ScreenColoredLoginFormController implements PageController{


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
    Button login_button;

    @Override
    public void setParams(Map<String, String> params) {}

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    @FXML
    public void initialize() {

        title_label.setText(Locales.get("login"));
        username_text_field.setPromptText(Locales.get("username"));
        password_text_field.setPromptText(Locales.get("password"));
        username_prompt.setText(String.format(Locales.get("register_page_username_field_prompt"), minUsernameLength, maxUsernameLength));
        password_prompt.setText(String.format(Locales.get("register_page_password_field_prompt"), minPasswordLength, maxPasswordLength));
        login_button.setText(Locales.get("login"));

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
    public void onLoginButtonClick() {

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

        //User.getInstance().enqueueMessage(new LoginMessage(username_text_field.getText(), password_text_field.getText()));
        User.getInstance().login(username_text_field.getText(), password_text_field.getText());
    }
}
