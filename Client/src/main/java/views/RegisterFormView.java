package views;

import graphics_controllers.GraphicsController;
import graphics_controllers.RegisterFormViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

    private Button registerButton;
    private TextField usernameTextField;
    private PasswordField passwordTextField;
    private RadioButton studentRadioButton;
    private RadioButton teacherRadioButton;
    private Button backButton;

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
        root.setPadding(new Insets(ViewNavigator.scaleValue(30)));

        ImageView imageView = new ImageViewWrapper(Icon.APPICON.getPath(), ViewNavigator.scaleValue(150), ViewNavigator.scaleValue(200));
        Label title = new LabelWrapper(Locales.get("register"), ViewNavigator.scaleValue(60), new Insets(0, 0, ViewNavigator.scaleValue(50), 0));

        registerButton = new ButtonWrapper(Locales.get("register"), ViewNavigator.scaleValue(40), new Insets(ViewNavigator.scaleValue(15), 0, 0, 0));
        registerButton.setId("register_button");
        registerButton.getStyleClass().add("form_button");
        VBox.setVgrow(registerButton, Priority.ALWAYS);
        registerButton.setMaxWidth(Float.MAX_VALUE);

        usernameTextField = new TextFieldWrapper(Locales.get("username"), ViewNavigator.scaleValue(40));
        usernameTextField.setAlignment(Pos.CENTER);
        Label usernamePrompt = new LabelWrapper(String.format(Locales.get("register_page_username_field_prompt"), Utils.getMinUsernameLength(), Utils.getMaxUsernameLength()), ViewNavigator.scaleValue(30));
        usernamePrompt.getStyleClass().add("prompt");

        passwordTextField = new PasswordFieldWrapper(Locales.get("password"), ViewNavigator.scaleValue(40), new Insets(ViewNavigator.scaleValue(10), 0, 0, 0));
        passwordTextField.setAlignment(Pos.CENTER);
        Label passwordPrompt = new LabelWrapper(String.format(Locales.get("register_page_password_field_prompt"), Utils.getMinPasswordLength(), Utils.getMaxPasswordLength()), ViewNavigator.scaleValue(30), new Insets(0, 0, ViewNavigator.scaleValue(30), 0));
        passwordPrompt.getStyleClass().add("prompt");

        HBox radioButtonsContainer = new HBox();
        radioButtonsContainer.setAlignment(Pos.CENTER);

        VBox studentVBox = new VBox();
        HBox.setHgrow(studentVBox, Priority.ALWAYS);
        studentVBox.setAlignment(Pos.CENTER);
        studentRadioButton = new RadioButton();
        Label studentLabel = new LabelWrapper(Locales.get("student"), ViewNavigator.scaleValue(25));
        studentVBox.getChildren().add(studentRadioButton);
        studentVBox.getChildren().add(studentLabel);
        studentRadioButton.setFocusTraversable(false);
        studentRadioButton.setSelected(true);

        VBox teacherVBox = new VBox();
        HBox.setHgrow(teacherVBox, Priority.ALWAYS);
        teacherVBox.setAlignment(Pos.CENTER);
        teacherRadioButton = new RadioButton();
        Label teacherLabel = new LabelWrapper(Locales.get("teacher"), ViewNavigator.scaleValue(25));
        teacherVBox.getChildren().add(teacherRadioButton);
        teacherVBox.getChildren().add(teacherLabel);
        teacherRadioButton.setFocusTraversable(false);

        radioButtonsContainer.getChildren().addAll(studentVBox, teacherVBox);

        backButton = new ButtonWrapper(new ImageViewWrapper("/colored/styles/icons/back-button.png", ViewNavigator.scaleValue(80), ViewNavigator.scaleValue(150)), new Insets(ViewNavigator.scaleValue(20), 0, ViewNavigator.scaleValue(10), 0));
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

    public Button getRegisterButton() {
        return registerButton;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public RadioButton getStudentRadioButton() {
        return studentRadioButton;
    }

    public RadioButton getTeacherRadioButton() {
        return teacherRadioButton;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public Button getBackButton() {
        return backButton;
    }
}
