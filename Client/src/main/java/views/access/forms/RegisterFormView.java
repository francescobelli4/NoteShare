package views.access.forms;

import graphics_controllers.GraphicsController;
import graphics_controllers.access.forms.RegisterFormViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import locales.Locales;
import utils.Utils;
import views.Page;
import views.View;

public class RegisterFormView implements View {

    @FXML
    private VBox root;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label usernamePrompt;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label passwordPrompt;
    @FXML
    private RadioButton studentRadioButton;
    @FXML
    private Label studentLabel;
    @FXML
    private RadioButton teacherRadioButton;
    @FXML
    private Label teacherLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;


    private static final Page page = Page.REGISTER_FORM;
    private final GraphicsController<RegisterFormView> graphicsController;

    public RegisterFormView() {
        graphicsController = new RegisterFormViewController(this);
        init();
    }

    @Override
    public void init() {

        Utils.scaleFonts(root);

        titleLabel.setText(Locales.get("register"));
        usernameTextField.setPromptText(Locales.get("username"));
        usernamePrompt.setText(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()));
        passwordTextField.setPromptText(Locales.get("password"));
        passwordPrompt.setText(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()));
        studentLabel.setText(Locales.get("student"));
        teacherLabel.setText(Locales.get("teacher"));
        registerButton.setText(Locales.get("register"));

        usernameTextField.setTextFormatter(getTextFormatter(Utils.getMaxUsernameLength(), true));
        passwordTextField.setTextFormatter(getTextFormatter(Utils.getMaxPasswordLength(), true));
    }

    @Override
    public void close() {
        //Nothing to do...
    }

    private TextFormatter<?> getTextFormatter(int maxLength, boolean onlyAlphanumeric) {
        return new TextFormatter<>(change -> {
            int len = change.getControlNewText().length();

            if (onlyAlphanumeric) {
                return len < maxLength && Utils.isAlphanumeric(change.getText()) ? change : null;
            } else {
                return len < maxLength ? change : null;
            }
        });
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public GraphicsController<RegisterFormView> getGraphicsController() {
        return graphicsController;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public Label getUsernamePrompt() {
        return usernamePrompt;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public Label getPasswordPrompt() {
        return passwordPrompt;
    }

    public RadioButton getStudentRadioButton() {
        return studentRadioButton;
    }

    public Label getStudentLabel() {
        return studentLabel;
    }

    public RadioButton getTeacherRadioButton() {
        return teacherRadioButton;
    }

    public Label getTeacherLabel() {
        return teacherLabel;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
