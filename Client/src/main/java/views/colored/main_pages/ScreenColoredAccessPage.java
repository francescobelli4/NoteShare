package views.colored.main_pages;

import app.App;
import app.mvc.BoundaryManager;
import app.mvc.login.LoginBoundary;
import app.mvc.login.LoginResult;
import views.colored.Page;
import views.colored.forms.ScreenColoredAccessForm;
import views.colored.forms.ScreenColoredForm;
import views.colored.forms.ScreenColoredLoginForm;
import views.colored.forms.ScreenColoredRegisterForm;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Class that represents the access page.
 *
 * An access page is a main page and it allows the user to login in or register.
 *
 * An access page has only one slot (secondaryPageSlot) for secondary pages:
 *  -   ScreenColoredAccessForm
 *  -   ScreenColoredLoginForm
 *  -   ScreenColoredRegisterForm
 *
 *  The references to the children controllers are joined in ScreenColoredForm formChildController,
 *  that is the reference at the active child's controller as only one of the children can be active
 *  at the same time.
 */
public class ScreenColoredAccessPage extends ScreenColoredMainPage implements LoginBoundary.Listener {

    /** The slot for the secondary pages */
    @FXML
    VBox secondaryPageSlot;

    /** The generic child controller reference */
    private ScreenColoredForm formChildController;

    /**
     * Base constructor
     *
     * This constructor actually loads the FXMLLoader and sets the controller for the page
     */
    public ScreenColoredAccessPage() {
        super(Page.ACCESS);

        this.loader.setController(this);
        this.root = App.getGraphicsController().loadFXMLLoader(loader);

        BoundaryManager.getInstance().initializeViewMessagesBoundary();
        BoundaryManager.getInstance().initializeLoginBoundary();
        BoundaryManager.getInstance().getLoginBoundary().addListener(this);
    }

    /**
     * This function should display this page. When this page is displayed, it should automatically append
     * the access form.
     * It also tries to do the tokenLogin.
     */
    @Override
    public void display() {
        super.display();
        BoundaryManager.getInstance().getLoginBoundary().performTokenLogin();
        appendAccessForm();
    }

    /**
     * This function appends the access form to the secondary page slot.
     */
    public void appendAccessForm() {
        closeChild(formChildController);

        formChildController = new ScreenColoredAccessForm(this);
        formChildController.display(secondaryPageSlot);
    }

    /**
     * This function appends the register form to the secondary page slot
     */
    public void appendRegisterForm() {
        closeChild(formChildController);

        formChildController = new ScreenColoredRegisterForm(this);
        formChildController.display(secondaryPageSlot);
    }

    /**
     * This function appends the login form to the secondary page slot
     */
    public void appendLoginForm() {
        closeChild(formChildController);

        formChildController = new ScreenColoredLoginForm(this);
        formChildController.display(secondaryPageSlot);
    }


    /**
     * This function is a listener: if tokenLogin has success, this is called!
     */
    @Override
    public void onLoginSuccess() {
        Platform.runLater(() -> {
            ScreenColoredHomePage screenColoredHomePage = new ScreenColoredHomePage();
            screenColoredHomePage.display();
        });
    }

    @Override
    public void onLoginFailed(LoginResult loginResult) {
        // Nothing to do in this situation
    }
}
