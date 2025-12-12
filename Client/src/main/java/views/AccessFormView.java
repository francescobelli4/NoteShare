package views;

import graphics_controllers.AccessFormViewController;
import graphics_controllers.GraphicsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import locales.Locales;
import views.components.ButtonWrapper;
import views.components.ImageViewWrapper;
import views.components.LabelWrapper;

public class AccessFormView implements View {

    private Button registerButton;
    private Button loginButton;

    private final GraphicsController<AccessFormView> graphicsController;
    private VBox root;

    public AccessFormView() {
        this.graphicsController = new AccessFormViewController(this);
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
        root.setPadding(new Insets(30));

        ImageView imageView = new ImageViewWrapper(Icon.APPICON.getPath(), 150, 200);
        Label title = new LabelWrapper(Locales.get("note_share"), 75f, new Insets(0, 0, 100, 0));

        registerButton = new ButtonWrapper(Locales.get("register"), 44, new Insets(30, 0, 0, 0));
        registerButton.setId("register_button");
        registerButton.getStyleClass().add("form_button");
        VBox.setVgrow(registerButton, Priority.ALWAYS);
        registerButton.setMaxWidth(Float.MAX_VALUE);

        loginButton = new ButtonWrapper(Locales.get("login"), 44, new Insets(30, 0, 0, 0));
        loginButton.setId("login_button");
        loginButton.getStyleClass().add("form_button");
        VBox.setVgrow(loginButton, Priority.ALWAYS);
        loginButton.setMaxWidth(Float.MAX_VALUE);

        root.getChildren().addAll(imageView, title, registerButton, loginButton);

        graphicsController.setup();
    }

    public GraphicsController<AccessFormView> getGraphicsController() {
        return graphicsController;
    }

    public VBox getRoot() {
        return root;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
