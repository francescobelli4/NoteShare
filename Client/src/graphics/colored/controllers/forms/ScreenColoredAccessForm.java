package graphics.colored.controllers.forms;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.PageController;
import graphics.colored.controllers.main_pages.ScreenColoredAccessPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import locales.Locales;

/**
 * Class that represents the access form.
 */
public class ScreenColoredAccessForm extends ScreenColoredForm {

    /** FXML ELEMENTS */
    @FXML
    Button login_button;
    @FXML
    Button register_button;


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
        login_button.setText(Locales.get("login"));
        register_button.setText(Locales.get("register"));

        login_button.setOnAction(actionEvent -> onLoginButtonClicked());

        register_button.setOnAction(actionEvent -> onRegisterButtonClicked());
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

    /**
     * This function should actually show this page.
     * @param container the container that contains this page
     */
    @Override
    public void display(VBox container) {
        super.display(container);
    }
}
