package views.access.forms;

import graphics_controllers.GraphicsController;
import graphics_controllers.access.forms.AccessFormViewController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import locales.Locales;
import views.Page;
import views.View;

public class AccessFormView implements View {

    @FXML
    private VBox root;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    private final Page page = Page.ACCESS_FORM;
    private final GraphicsController<AccessFormView> graphicsController;

    public AccessFormView() {
        graphicsController = new AccessFormViewController(this);
        init();
    }

    @Override
    public void init() {
        registerButton.setText(Locales.get("register"));
        loginButton.setText(Locales.get("login"));
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
    public GraphicsController<AccessFormView> getGraphicsController() {
        return graphicsController;
    }

    @Override
    public void update() {
        //Not needed...
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }
}
