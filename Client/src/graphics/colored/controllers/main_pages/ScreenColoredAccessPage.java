package graphics.colored.controllers.main_pages;

import graphics.GraphicsController;
import graphics.colored.Page;
import graphics.colored.controllers.forms.ScreenColoredAccessForm;
import graphics.colored.controllers.forms.ScreenColoredForm;
import graphics.colored.controllers.forms.ScreenColoredLoginForm;
import graphics.colored.controllers.forms.ScreenColoredRegisterForm;
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
public class ScreenColoredAccessPage extends ScreenColoredMainPage {

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
        this.root = GraphicsController.getInstance().loadFXMLLoader(loader);
    }

    /**
     * This function should display this page. When this page is displayed, it should automatically append
     * the access form.
     */
    @Override
    public void display() {
        super.display();
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
     *  GETTERS
     */

    public ScreenColoredForm getFormChildController() {
        return formChildController;
    }

    public VBox getSecondaryPageSlot() {
        return secondaryPageSlot;
    }
}
