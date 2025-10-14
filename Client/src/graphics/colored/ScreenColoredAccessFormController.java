package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import locales.Locales;

import java.util.Map;

public class ScreenColoredAccessFormController implements PageController{

    @FXML
    private Button login_button;

    @FXML
    private Button register_button;

    @Override
    public void setParams(Map<String, String> params) {

    }

    @Override
    public void appendSecondaryPage(int id, Node secondaryPage) {

    }

    /**
     * At the start of the controller, I only need to set up the labels...
     */
    @FXML
    public void initialize() {
        login_button.setText(Locales.get("login"));
        register_button.setText(Locales.get("register"));
    }

    @FXML
    private void onLoginButtonClick() {
        System.out.println("Login button clicked");
        GraphicsController.displaySecondaryPage(Pages.LOGIN_FORM, 0, null);
    }

    @FXML
    private void onRegisterButtonClick() {
        System.out.println("Register button clicked");
        GraphicsController.displaySecondaryPage(Pages.REGISTER_FORM, 0, null);
    }
}
