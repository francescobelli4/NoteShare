package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.LoginFormViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;
import utils.Utils;
import views.components.*;

import java.util.Objects;

public class LoginFormView implements View {

    private Button loginButton;
    private TextField usernameTextField;
    private PasswordField passwordTextField;
    private Button backButton;

    private final GraphicsController<LoginFormView> graphicsController;
    private VBox root;

    public LoginFormView() {
        this.graphicsController = new LoginFormViewController(this);
        init();
    }

    @Override
    public void update() {
        //Nothing to do...
    }

    @Override
    public void init() {
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("form");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/colored/styles/RegisterForm.css")).toExternalForm());
        assert root != null;
        root.setPadding(new Insets(ViewNavigator.scaleValue(30)));

        ImageView imageView = new ImageViewWrapper(Icon.APPICON.getPath(), ViewNavigator.scaleValue(150), ViewNavigator.scaleValue(200));
        Label title = new LabelWrapper(Locales.get("login"), ViewNavigator.scaleValue(60), new Insets(0, 0, ViewNavigator.scaleValue(50), 0));

        loginButton = new ButtonWrapper(Locales.get("login"), ViewNavigator.scaleValue(40), new Insets(ViewNavigator.scaleValue(15), 0, 0, 0));
        loginButton.setId("register_button");
        loginButton.getStyleClass().add("form_button");
        VBox.setVgrow(loginButton, Priority.ALWAYS);
        loginButton.setMaxWidth(Float.MAX_VALUE);

        usernameTextField = new TextFieldWrapper(Locales.get("username"), ViewNavigator.scaleValue(40));
        usernameTextField.setAlignment(Pos.CENTER);
        Label usernamePrompt = new LabelWrapper(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()), ViewNavigator.scaleValue(30));
        usernamePrompt.getStyleClass().add("prompt");

        passwordTextField = new PasswordFieldWrapper(Locales.get("password"), ViewNavigator.scaleValue(40), new Insets(ViewNavigator.scaleValue(10), 0, 0, 0));
        passwordTextField.setAlignment(Pos.CENTER);
        Label passwordPrompt = new LabelWrapper(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()), ViewNavigator.scaleValue(30));
        passwordPrompt.getStyleClass().add("prompt");

        backButton = new ButtonWrapper(new ImageViewWrapper("/colored/styles/icons/back-button.png", ViewNavigator.scaleValue(80), ViewNavigator.scaleValue(150)), new Insets(ViewNavigator.scaleValue(20), 0, ViewNavigator.scaleValue(10), 0));
        backButton.setId("back_button");

        root.getChildren().addAll(imageView, title, usernameTextField, usernamePrompt, passwordTextField, passwordPrompt, loginButton, backButton);

        graphicsController.setup();
    }

    public GraphicsController<LoginFormView> getGraphicsController() {
        return graphicsController;
    }

    public VBox getRoot() {
        return root;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public Button getBackButton() {
        return backButton;
    }
}
