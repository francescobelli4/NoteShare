package views.colored.forms;

import views.GraphicsController;
import views.colored.Page;
import views.colored.PageController;
import views.colored.main_pages.ScreenColoredAccessPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import locales.Locales;

/**
 * Class that represents the access form.
 */
public class ScreenColoredAccessForm extends ScreenColoredForm {

    /** FXML ELEMENTS */
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;


    /**
     * Constructor with parent controller
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     * @param parentController the controller of the parent page
     */
    public ScreenColoredAccessForm(PageController parentController) {
        super(Page.ACCESS_FORM, parentController);

        this.loader.setController(this);
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * In the initialize function, I set all the labels and the button click events
     */
    @FXML
    public void initialize() {
        loginButton.setText(Locales.get("login"));
        registerButton.setText(Locales.get("register"));

        loginButton.setOnAction(_ -> onLoginButtonClicked());

        registerButton.setOnAction(_ -> onRegisterButtonClicked());
    }

    /**
     * This function should append the login form to the parent child slot
     */
    private void onLoginButtonClicked() {
        ((ScreenColoredAccessPage) this.parentController).appendLoginForm();
    }

    /**
     * This function should append the register form to the parent child slot
     */
    private void onRegisterButtonClicked() {
        ((ScreenColoredAccessPage) this.parentController).appendRegisterForm();
    }

    /**
     * This function should close this page. It only needs to clear the parent's child slot
     */
    @Override
    public void close() {
        this.container.getChildren().clear();
    }
}
