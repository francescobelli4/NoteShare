package graphics.colored;

import graphics.GraphicsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import locales.Locales;
import java.util.Map;

/**
 * This controller manages the AccessPage.
 */
public class ScreenColoredAccessController implements PageController{

    @FXML
    private Button login_button;

    @FXML
    private Button register_button;

    /**
     * This page needs no params to be started
     */
    @Override
    public void setParams(Map<String, String> params) {}

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
    }

    @FXML
    private void onRegisterButtonClick() {
        System.out.println("Register button clicked");
        //GraphicsController.displayMainPage(Pages.REGISTER);
    }
}
