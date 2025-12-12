package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.RegisterFormViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;
import utils.Utils;
import views.components.*;

import java.util.Objects;

public class RegisterFormView implements View {

    public Button registerButton;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public RadioButton studentRadioButton;
    public RadioButton teacherRadioButton;
    public Button backButton;

    private final GraphicsController<RegisterFormView> graphicsController;
    private VBox root;

    public RegisterFormView() {
        this.graphicsController = new RegisterFormViewController(this);
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
        root.setPadding(new Insets(30));

        ImageView imageView = new ImageViewWrapper(Icon.APPICON.getPath(), 150, 200);
        Label title = new LabelWrapper(Locales.get("register"), 75f, new Insets(0, 0, 70, 0));

        registerButton = new ButtonWrapper(Locales.get("register"), 44, new Insets(30, 0, 0, 0));
        registerButton.setId("register_button");
        registerButton.getStyleClass().add("form_button");
        VBox.setVgrow(registerButton, Priority.ALWAYS);
        registerButton.setMaxWidth(Float.MAX_VALUE);

        usernameTextField = new TextFieldWrapper(Locales.get("username"), 45, new Insets(10, 0, 0, 0));
        usernameTextField.setAlignment(Pos.CENTER);
        Label usernamePrompt = new LabelWrapper(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()), 31);
        usernamePrompt.getStyleClass().add("prompt");

        passwordTextField = new PasswordFieldWrapper(Locales.get("password"), 45, new Insets(30, 0, 0, 0));
        passwordTextField.setAlignment(Pos.CENTER);
        Label passwordPrompt = new LabelWrapper(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()), 31);
        passwordPrompt.getStyleClass().add("prompt");

        HBox radioButtonsContainer = new HBox();
        radioButtonsContainer.setAlignment(Pos.CENTER);

        VBox studentVBox = new VBox();
        HBox.setHgrow(studentVBox, Priority.ALWAYS);
        studentVBox.setAlignment(Pos.CENTER);
        studentRadioButton = new RadioButton();
        Label studentLabel = new LabelWrapper(Locales.get("student"), 25);
        studentVBox.getChildren().add(studentRadioButton);
        studentVBox.getChildren().add(studentLabel);
        studentRadioButton.setFocusTraversable(false);
        studentRadioButton.setSelected(true);

        VBox teacherVBox = new VBox();
        HBox.setHgrow(teacherVBox, Priority.ALWAYS);
        teacherVBox.setAlignment(Pos.CENTER);
        teacherRadioButton = new RadioButton();
        Label teacherLabel = new LabelWrapper(Locales.get("teacher"), 25);
        teacherVBox.getChildren().add(teacherRadioButton);
        teacherVBox.getChildren().add(teacherLabel);
        teacherRadioButton.setFocusTraversable(false);

        radioButtonsContainer.getChildren().addAll(studentVBox, teacherVBox);

        backButton = new ButtonWrapper(new ImageViewWrapper("/colored/styles/icons/back-button.png", 80, 150), new Insets(40, 0, 15, 0));
        backButton.setId("back_button");

        root.getChildren().addAll(imageView, title, usernameTextField, usernamePrompt, passwordTextField, passwordPrompt, radioButtonsContainer, registerButton, backButton);

        graphicsController.setup();
    }

    public GraphicsController<RegisterFormView> getGraphicsController() {
        return graphicsController;
    }

    public VBox getRoot() {
        return root;
    }
}
